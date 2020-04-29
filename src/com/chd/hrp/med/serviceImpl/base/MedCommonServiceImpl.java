package com.chd.hrp.med.serviceImpl.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.info.basic.MedStoreMapper;
import com.chd.hrp.med.service.base.MedCommonService;
import com.github.pagehelper.PageInfo;

@Service("medCommonService")
public class MedCommonServiceImpl implements MedCommonService {
	
	private static Logger logger = Logger.getLogger(MedCommonServiceImpl.class);
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	//引入DAO操作
	@Resource(name = "medStoreMapper")
	private final MedStoreMapper medStoreMapper = null;
	
	@Override
	public String getMedHosRules(Map<String, Object> entityMap) throws DataAccessException {
		
		return medCommonMapper.getMedHosRules(entityMap);
	}
	
	@Override
	public String getMedAccPara(Map<String, Object> entityMap) throws DataAccessException {
		
		return medCommonMapper.getMedAccPara(entityMap);
	}
	
	/**
	 * 根据当前个体码生成下一个个体码
	 * @param bar_code(流水码中当前个体码)
	 * @return
	 */
	@Override
	public String getNextBar_code(String bar_code){
		//String转char数组
		char[] chars = bar_code.toCharArray(); 
		
		//读取条码生成方式
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("para_code", "04099");
		String flag = medCommonMapper.getMedAccPara(entityMap); //0: 纯数字，1: 数字和字母组合
		
		if(flag != null && "1".equals(flag)){ 
			for(int i = chars.length - 1; i >=0; i--){
				//如果char的ASCII码值为122说明当前值为z，加一后值为0
				if((int)chars[i] == 122){
					chars[i] = '0';
				}else{
					//如果char的ASCII码值为57说明当前值为9，加一后值为a
					if((int)chars[i] == 57){
						chars[i] = 'a';
					}else{//其他值则为ASCII码值加一后的char值
						chars[i] = (char)((int)chars[i] + 1);
					}
					//不存在进一，则前面的值不变，所以跳出循环
					break;
				}
			}
		}else{
			for(int i = chars.length - 1; i >=0; i--){
				//如果char的ASCII码值大于等于57说明当前值为9或者字母，则值变为0进入下一循环
				if((int)chars[i] >= 57){
					chars[i] = '0';
				}else{
					//其他值则为ASCII码值加一后的char值
					chars[i] = (char)((int)chars[i] + 1);
					//不存在进一，则前面的值不变，所以跳出循环
					break;
				}
			}
		}
		
		//获得新条码
		bar_code = new String(chars);
		
		//如果条码达到最大则增加位数
		if(NumberUtil.isNumeric(bar_code) && Integer.parseInt(bar_code) == 0){
			bar_code = "1" + bar_code;
		}
		
		return bar_code;
	}
	//材料字典列表(有材料库存)
	@Override
	public String queryMedInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//材料字典列表(有材料库存)  科室申领
		@Override
		public String queryMedInvListApply(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = medCommonMapper.queryMedInvListApply(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = medCommonMapper.queryMedInvListApply(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		
	
	
	//材料字典列表(专购品入库)
		@Override
		public String queryMedInvListSpecial(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = medCommonMapper.queryMedInvListSpecial(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = medCommonMapper.queryMedInvListSpecial(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
	
	//材料字典列表(没有材料库存,不带仓库)
	@Override
	public String queryMedInvListNotStore(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
			
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedInvListNotStore(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedInvListNotStore(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//代销材料字典列表
	@Override
	public String queryMedAffiInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedAffiInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedAffiInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 普通库存材料列表（不含批次）  库存盘点
	@Override
	public String queryMedStockInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvList(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	// 普通库存材料列表（不含批次）  材料调拨 
		@Override
		public String queryMedStockInvListTran(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListTran(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListTran(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
	
	// 普通库存材料列表（不含批次） 材料出库 
		@Override
		public String queryMedStockInvListOut(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListOut(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListOut(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
		
	// 普通库存材料列表（不含批次）材料退货 
		@Override
		public String queryMedStockInvListBack(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListBack(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListBack(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
		
	// 普通库存材料明细列表（含批次）
	@Override
	public String queryMedStockInvDetailList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(entityMap.containsKey("amount")){
			if(Integer.parseInt(entityMap.get("amount").toString()) > 0){
				entityMap.put("flag", "1");
			}else{
				entityMap.put("flag", "0");
			}
		}else{
			entityMap.put("flag", "1");
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
	}
	
	
	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override
	public String getInvJsonByInvList(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			
			//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
			Map<String, Map<String, Object>> invJsonMap = new HashMap<String, Map<String, Object>>();
			
			//出库材料Map，用于判断库存(key : inv_id，value：材料库存List)
			Map<String, List<Map<String, Object>>> immeMap = new HashMap<String, List<Map<String,Object>>>();

			String invKey = "";  //材料Map的key值
			String invJsonKey = "";  //材料JsonMap的key值
			String immeKey = "";  //材料Map的key值
			boolean is_back = false;  //是否退库
			double amount;  //当前材料的出库数量
			double imme_amount = 0;  //当前材料当前批次的即时库存数量
			boolean is_check_imme;  //是否判断库存不足，如果否则库存剩余多少生成多少
			boolean isInvAlready = false;  //判断材料是否存在Map中
			boolean isInvJsonAlready = false;  //判断材料是否存在Map中
			StringBuffer invEnoughMsg = new StringBuffer();  //记录库存不足的材料
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				amount = Double.valueOf(entityMap.get("amount") == null ? "0" : entityMap.get("amount").toString());
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				is_check_imme = true;
				
				double do_amount = 0;
				immeKey = entityMap.get("inv_id").toString() + (entityMap.get("batch_no") == null ? "" : entityMap.get("batch_no").toString()) + (entityMap.get("bar_code") == null ? "" : entityMap.get("bar_code").toString());
				//先进先出材料列表
				List<Map<String, Object>> fifoList;
				if(immeMap.containsKey(immeKey)){
					fifoList = immeMap.get(immeKey);
				}else{
					//fifo帐表信息
					if(entityMap.containsKey("amount")){
						if(Integer.parseInt(entityMap.get("amount").toString()) > 0){
							entityMap.put("flag", "1");
						}else{
							entityMap.put("flag", "0");
						}
					}else{
						entityMap.put("flag", "1");
					}
					fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(entityMap));
				}
				
				Map<String,Object> detailMap =new HashMap<String,Object>();
				
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("store_id", entityMap.get("store_id"));
				detailMap.put("inv_id", entityMap.get("inv_id"));
				detailMap.put("inv_no", entityMap.get("inv_no"));
				detailMap.put("inv_code", entityMap.get("inv_code"));
				detailMap.put("inv_name", entityMap.get("inv_name"));
				detailMap.put("note", entityMap.get("note"));
				if(entityMap.get("is_check_imme") != null && "0".equals(entityMap.get("is_check_imme").toString())){
					is_check_imme = false;
				}
				
				//StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				//获取该材料各批次数据
				if (fifoList.size() > 0) {
					for (Map<String, Object> fifoMap : fifoList) {
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
						if(imme_amount == 0){
							continue;
						}
						
						//获取invKey
						invKey = entityMap.get("inv_id").toString() + fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();
						if(amount > 0){
							invJsonKey = invKey + fifoMap.get("batch_sn").toString();
						}
						
						//初始化变量--材料是否存在
						isInvAlready = false;
						if(invMap.containsKey(invKey)){
							//获取已有的detailMap
							detailMap.putAll(invMap.get(invKey));
							isInvAlready = true;
						}
						Map<String,Object> detailJsonMap;
						if(invJsonMap.containsKey(invJsonKey)){
							//获取已有的detailJsonMap
							detailJsonMap = invJsonMap.get(invJsonKey);
							isInvJsonAlready = true;
						}else{
							//新建detailJsonMap
							detailJsonMap = new HashMap<String,Object>();
							isInvJsonAlready=false;
						}
						
						//材料是否已出完
						if(amount > 0){
							/******************材料主信息********begin******************************/
							if(!isInvAlready){
								//如果该材料不存在则需要添加新的明细记录
								/**---------------------数量初始化--begin---------------------*/
								detailMap.put("amount", 0);
								detailMap.put("sum_amount", 0);
								detailMap.put("cur_amount", 0);
								detailMap.put("imme_amount", 0);
								detailMap.put("amount_money", 0);
								detailMap.put("sale_money", 0);
								detailMap.put("sell_money", 0);
								/**---------------------数量初始化--end-----------------------*/
								detailMap.put("store_id", entityMap.get("store_id"));
								detailMap.put("sup_id", entityMap.get("sup_id"));
								detailMap.put("sup_no", entityMap.get("sup_no"));
								detailMap.put("sup_name", entityMap.get("sup_name"));
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
								detailMap.put("cur_amount", fifoMap.get("all_cur_amount"));
								detailMap.put("imme_amount", fifoMap.get("all_imme_amount"));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}else{
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("all_cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("all_imme_amount").toString()));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}
							/******************材料主信息********end********************************/
							/******************材料批次信息******begin******************************/
							if(!isInvJsonAlready){
								detailJsonMap.put("inv_id", fifoMap.get("inv_id"));
								detailJsonMap.put("inv_no", fifoMap.get("inv_no"));
								detailJsonMap.put("inv_code", fifoMap.get("inv_code"));
								detailJsonMap.put("inv_name", fifoMap.get("inv_name"));
								detailJsonMap.put("batch_sn", fifoMap.get("batch_sn"));
								detailJsonMap.put("cur_amount", fifoMap.get("cur_amount"));
								detailJsonMap.put("imme_amount", fifoMap.get("imme_amount"));
								detailJsonMap.put("price", fifoMap.get("price"));
								detailJsonMap.put("sale_price", fifoMap.get("sale_price"));
								detailJsonMap.put("sell_price", fifoMap.get("sell_price"));
								detailJsonMap.put("amount", 0);
								detailJsonMap.put("amount_money", 0);
								detailJsonMap.put("sale_money", 0);
								detailJsonMap.put("sell_money", 0);
							}
							
							//本批次数量充足直接出库
							if(amount <= imme_amount){
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));

								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								fifoMap.put("imme_amount", NumberUtil.sub(imme_amount, amount));
								do_amount += amount;  //实际处理数量
								amount = 0.0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + imme_amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + imme_amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + imme_amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								fifoMap.put("imme_amount", 0);
								do_amount += imme_amount;  //实际处理数量
								amount = NumberUtil.sub(amount, imme_amount);
							}
							/******************材料批次信息******end********************************/
						}else{
							/**数量为0判断是否继续累加库存还是跳出循环********begin***************/
							if(isInvAlready){
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("all_cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("all_imme_amount").toString()));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}else{
								//记录材料信息，并跳出循环
								if(detailMap.get("inv_id") != null){
									//处理科室申领对应关系
									if(entityMap.get("apply_id") != null && !"".equals(entityMap.get("apply_id").toString()) && do_amount != 0){
										String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
										if("".equals(other_ids)){
											other_ids = entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
										}else{
											other_ids += "," + entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
										}
										do_amount = 0;
										detailMap.put("other_ids", other_ids);
									}
									//存储
									invMap.put(invKey, detailMap);
									invJsonMap.put(invJsonKey, detailJsonMap);
								}
								break;
							}
							/**数量为0判断是否继续累加库存还是跳出循环********end*****************/
						}
						//记录材料信息
						if(detailMap.get("inv_id") != null){
							//处理科室申领对应关系
							if(entityMap.get("apply_id") != null && !"".equals(entityMap.get("apply_id").toString()) && do_amount != 0){
								String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
								if("".equals(other_ids)){
									other_ids = entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
								}else{
									other_ids += "," + entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
								}
								do_amount = 0;
								detailMap.put("other_ids", other_ids);
							}
							//存储
							invMap.put(invKey, detailMap);
							invJsonMap.put(invJsonKey, detailJsonMap);
						}
						
						detailMap = new HashMap<String, Object>();
						do_amount = 0;
					}

					immeMap.put(immeKey, fifoList);
				}else{
					if(is_check_imme){
						invEnoughMsg.append(detailMap.get("inv_code")).append(" ").append(detailMap.get("inv_name")).append("<br>");
					}
				}
			}
			
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						detailJson.append("{\"inv_id\":").append(detailJsonMap.get("inv_id"))
							.append(",\"inv_no\":").append(detailJsonMap.get("inv_no"))
							.append(",\"inv_code\":\"").append(detailJsonMap.get("inv_code"))
							.append("\",\"inv_name\":\"").append(detailJsonMap.get("inv_name"))
							.append("\",\"batch_sn\":").append(detailJsonMap.get("batch_sn"))
							.append(",\"cur_amount\":").append(detailJsonMap.get("cur_amount"))
							.append(",\"imme_amount\":").append(detailJsonMap.get("imme_amount"))
							.append(",\"price\":").append(detailJsonMap.get("price"))
							.append(",\"sale_price\":").append(detailJsonMap.get("sale_price"))
							.append(",\"sell_price\":").append(detailJsonMap.get("sell_price"))
							.append(",\"amount\":").append(detailJsonMap.get("amount"))
							.append(",\"amount_money\":").append(detailJsonMap.get("amount_money"))
							.append(",\"sale_money\":").append(detailJsonMap.get("sale_money"))
							.append(",\"sell_money\":").append(detailJsonMap.get("sell_money"))
							.append("},");
					}
				}
				detailJson.substring(0, detailJson.length()-1);
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString());
				detailList.add(detailMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存药品不足：<br>"+invEnoughMsg.toString()+"\"}";
			}
			if(detailList.size() > 0){
				return ChdJson.toJson(detailList);
			}else{
				return "{\"error\":\"所选数据材料库存不足！\"}";
			}
			
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成材料明细失败 数据库异常 请联系管理员! 方法 getInvJsonByInvList\"}";
		}
	}

	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override 
	public String getAffiInvJsonByAffiInvList(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			
			//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
			Map<String, Map<String, Object>> invJsonMap = new HashMap<String, Map<String, Object>>();
			
			//出库材料Map，用于判断库存(key : inv_id，value：材料库存List)
			Map<String, List<Map<String, Object>>> immeMap = new HashMap<String, List<Map<String,Object>>>();

			String invKey = "";  //材料Map的key值
			String invJsonKey = "";  //材料JsonMap的key值
			String immeKey = "";  //材料Map的key值
			boolean is_back = false;  //是否退库
			double amount;  //当前材料的出库数量
			double imme_amount = 0;  //当前材料当前批次的即时库存数量
			boolean is_check_imme;  //是否判断库存不足，如果否则库存剩余多少生成多少
			boolean isInvAlready = false;  //判断材料是否存在Map中
			boolean isInvJsonAlready = false;  //判断材料是否存在Map中
			StringBuffer invEnoughMsg = new StringBuffer();  //记录库存不足的材料
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				amount = Double.valueOf(entityMap.get("amount") == null ? "0" : entityMap.get("amount").toString());
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				is_check_imme = true;
				
				double do_amount = 0;
				immeKey = entityMap.get("inv_id").toString() + (entityMap.get("batch_no") == null ? "" : entityMap.get("batch_no").toString()) + (entityMap.get("bar_code") == null ? "" : entityMap.get("bar_code").toString());
				//先进先出材料列表
				List<Map<String, Object>> fifoList;
				if(immeMap.containsKey(immeKey)){
					fifoList = immeMap.get(immeKey);
				}else{
					//fifo帐表信息
					fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedAffiOutDetailInvList(entityMap));
				}
				
				Map<String,Object> detailMap =new HashMap<String,Object>();
				
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("store_id", entityMap.get("store_id"));
				detailMap.put("inv_id", entityMap.get("inv_id"));
				detailMap.put("inv_no", entityMap.get("inv_no"));
				detailMap.put("inv_code", entityMap.get("inv_code"));
				detailMap.put("inv_name", entityMap.get("inv_name"));
				detailMap.put("sup_id", entityMap.get("sup_id"));
				detailMap.put("sup_no", entityMap.get("sup_no"));
				detailMap.put("sup_name", entityMap.get("sup_name"));
				if(entityMap.get("is_check_imme") != null && "0".equals(entityMap.get("is_check_imme").toString())){
					is_check_imme = false;
				}
				
				//StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				//获取该材料各批次数据
				if (fifoList.size() > 0) {
					for (Map<String, Object> fifoMap : fifoList) {
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
						if(imme_amount == 0){
							continue;
						}
						
						//获取invKey
						invKey = entityMap.get("inv_id").toString() + fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();
						if(amount > 0){
							invJsonKey = invKey + fifoMap.get("batch_sn").toString();
						}
						
						//初始化变量--材料是否存在
						isInvAlready = false;
						if(invMap.containsKey(invKey)){
							//获取已有的detailMap
							detailMap.putAll(invMap.get(invKey));
							isInvAlready = true;
						}
						Map<String,Object> detailJsonMap;
						if(invJsonMap.containsKey(invJsonKey)){
							//获取已有的detailJsonMap
							detailJsonMap = invJsonMap.get(invJsonKey);
							isInvJsonAlready = true;
						}else{
							//新建detailJsonMap
							detailJsonMap = new HashMap<String,Object>();
							isInvJsonAlready=false;
						}
						
						//材料是否已出完
						if(amount > 0){
							/******************材料主信息********begin******************************/
							if(!isInvAlready){
								//如果该材料不存在则需要添加新的明细记录
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
								detailMap.put("cur_amount", fifoMap.get("all_cur_amount"));
								detailMap.put("imme_amount", fifoMap.get("all_imme_amount"));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}else{
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("all_cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("all_imme_amount").toString()));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}
							/******************材料主信息********end********************************/
							/******************材料批次信息******begin******************************/
							if(!isInvJsonAlready){
								detailJsonMap.put("inv_id", fifoMap.get("inv_id"));
								detailJsonMap.put("inv_no", fifoMap.get("inv_no"));
								detailJsonMap.put("inv_code", fifoMap.get("inv_code"));
								detailJsonMap.put("inv_name", fifoMap.get("inv_name"));
								detailJsonMap.put("batch_sn", fifoMap.get("batch_sn"));
								detailJsonMap.put("cur_amount", fifoMap.get("cur_amount"));
								detailJsonMap.put("imme_amount", fifoMap.get("imme_amount"));
								detailJsonMap.put("price", fifoMap.get("price"));
								detailJsonMap.put("sale_price", fifoMap.get("sale_price"));
								detailJsonMap.put("sell_price", fifoMap.get("sell_price"));
								detailJsonMap.put("amount", 0);
								detailJsonMap.put("amount_money", 0);
								detailJsonMap.put("sale_money", 0);
								detailJsonMap.put("sell_money", 0);
							}
							
							//本批次数量充足直接出库
							if(amount <= imme_amount){
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));

								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								fifoMap.put("imme_amount", NumberUtil.sub(imme_amount, amount));
								do_amount += amount;  //实际处理数量
								amount = 0.0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + imme_amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + imme_amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + imme_amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								fifoMap.put("imme_amount", 0);
								do_amount += imme_amount;  //实际处理数量
								amount = NumberUtil.sub(amount, imme_amount);
							}
							/******************材料批次信息******end********************************/
						}else{
							/**数量为0判断是否继续累加库存还是跳出循环********begin***************/
							if(isInvAlready){
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("all_cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("all_imme_amount").toString()));
								//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
								fifoMap.put("all_cur_amount", 0);
								fifoMap.put("all_imme_amount", 0);
							}else{
								//记录材料信息，并跳出循环
								if(detailMap.get("inv_id") != null){
									//处理科室申领对应关系
									if(entityMap.get("apply_id") != null && !"".equals(entityMap.get("apply_id").toString()) && do_amount != 0){
										String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
										if("".equals(other_ids)){
											other_ids = entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
										}else{
											other_ids += "," + entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
										}
										do_amount = 0;
										detailMap.put("other_ids", other_ids);
									}
									//存储
									invMap.put(invKey, detailMap);
									invJsonMap.put(invJsonKey, detailJsonMap);
								}
								break;
							}
							/**数量为0判断是否继续累加库存还是跳出循环********end*****************/
						}
						//记录材料信息
						if(detailMap.get("inv_id") != null){
							//处理科室申领对应关系
							if(entityMap.get("apply_id") != null && !"".equals(entityMap.get("apply_id").toString()) && do_amount != 0){
								String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
								if("".equals(other_ids)){
									other_ids = entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
								}else{
									other_ids += "," + entityMap.get("apply_id").toString() + "@" + entityMap.get("detail_id").toString() + "@" + do_amount;
								}
								do_amount = 0;
								detailMap.put("other_ids", other_ids);
							}
							//存储
							invMap.put(invKey, detailMap);
							invJsonMap.put(invJsonKey, detailJsonMap);
						}
						
						detailMap = new HashMap<String, Object>();
						do_amount = 0;
					}

					immeMap.put(immeKey, fifoList);
				}else{
					if(is_check_imme){
						invEnoughMsg.append(detailMap.get("inv_code")).append(" ").append(detailMap.get("inv_name")).append("<br>");
					}
				}
			}
			
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						detailJson.append("{\"inv_id\":").append(detailJsonMap.get("inv_id"))
							.append(",\"inv_no\":").append(detailJsonMap.get("inv_no"))
							.append(",\"inv_code\":\"").append(detailJsonMap.get("inv_code"))
							.append("\",\"inv_name\":\"").append(detailJsonMap.get("inv_name"))
							.append("\",\"batch_sn\":").append(detailJsonMap.get("batch_sn"))
							.append(",\"cur_amount\":").append(detailJsonMap.get("cur_amount"))
							.append(",\"imme_amount\":").append(detailJsonMap.get("imme_amount"))
							.append(",\"price\":").append(detailJsonMap.get("price"))
							.append(",\"sale_price\":").append(detailJsonMap.get("sale_price"))
							.append(",\"sell_price\":").append(detailJsonMap.get("sell_price"))
							.append(",\"amount\":").append(detailJsonMap.get("amount"))
							.append(",\"amount_money\":").append(detailJsonMap.get("amount_money"))
							.append(",\"sale_money\":").append(detailJsonMap.get("sale_money"))
							.append(",\"sell_money\":").append(detailJsonMap.get("sell_money"))
							.append("},");
					}
				}
				detailJson.substring(0, detailJson.length()-1);
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString());
				detailList.add(detailMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存药品不足：<br>"+invEnoughMsg.toString()+"\"}";
			}
			if(detailList.size() > 0){
				return ChdJson.toJson(detailList);
			}else{
				return "{\"error\":\"所选数据材料库存不足！\"}";
			}
			
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成材料明细失败 数据库异常 请联系管理员! 方法 getInvJsonByInvList\"}";
		}
	}
	
	
	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	//@Override
	public String getInvJsonByInvListOld(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			//记录库存不足的材料
			StringBuffer invEnoughMsg = new StringBuffer();
			//是否不判断库存不足，如果是则库存剩余多少生成多少
			boolean is_create_left = false;
			//当前材料的出库数量
			double amount;
			//材料基础信息
			for(Map<String, Object> entityMap : entityList){
				is_create_left = false;
				Map<String,Object> mapDetailVo =new HashMap<String,Object>();
				amount = Double.parseDouble(String.valueOf(entityMap.get("amount")));
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				mapDetailVo.put("group_id", entityMap.get("group_id"));
				mapDetailVo.put("hos_id", entityMap.get("hos_id"));
				mapDetailVo.put("copy_code", entityMap.get("copy_code"));
				mapDetailVo.put("store_id", entityMap.get("store_id"));
				mapDetailVo.put("inv_id", entityMap.get("inv_id"));
				mapDetailVo.put("inv_no", entityMap.get("inv_no"));
				mapDetailVo.put("inv_code", entityMap.get("inv_code"));
				mapDetailVo.put("inv_name", entityMap.get("inv_name"));
				//用于科室申请
				if(entityMap.get("detail_id") != null){
					mapDetailVo.put("detail_id", entityMap.get("detail_id"));
				}
				if(entityMap.get("is_create_left") != null && "1".equals(entityMap.get("is_create_left").toString())){
					is_create_left = true;
				}
				
				StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				String batchBar = "";  //用于判断批号条码是否相同
				String newBatchBar = "";  //用于判断批号条码是否相同
				boolean isNot_frist = false;  //是否不是第一次循环
				//获取该材料各批次数据
				if(entityMap.containsKey("amount")){
					if(Integer.parseInt(entityMap.get("amount").toString()) > 0){
						mapDetailVo.put("flag", "1");
					}else{
						mapDetailVo.put("flag", "0");
					}
				}else{
					mapDetailVo.put("flag", "1");
				}
				
				List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(mapDetailVo));
				if (list.size() > 0) {
					for (Map<String, Object> map : list) {
						newBatchBar = map.get("batch_no").toString() + map.get("bar_code").toString() + map.get("price").toString();
						
						//本批次材料剩余量
						double  imme_amount = Double.parseDouble(String.valueOf(map.get("imme_amount")));
						
						//材料是否已出完
						if(amount > 0 && imme_amount > 0){
							/*材料主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								//如果该材料第二次出现批号条码不对应则需要添加新的明细记录
								if(isNot_frist){
									inv_detail_data.substring(0, inv_detail_data.length()-1);
									inv_detail_data.append("]");
									Map<String, Object> mapDetailVoOld = new HashMap<String, Object>();
									mapDetailVoOld.putAll(mapDetailVo);
									mapDetailVoOld.put("inv_detail_data", inv_detail_data.toString());
									detailList.add(mapDetailVoOld);
									inv_detail_data = new StringBuffer();
								}
								/**---------------------数量初始化--begin---------------------*/
								mapDetailVo.put("amount", 0);
								mapDetailVo.put("sum_amount", 0);
								mapDetailVo.put("cur_amount", 0);
								mapDetailVo.put("imma_amount", 0);
								/**---------------------数量初始化--end-----------------------*/
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
								//以下3个属性用于调拨
								mapDetailVo.put("location_out_id", map.get("location_id"));
								mapDetailVo.put("location_out_code", map.get("location_code"));
								mapDetailVo.put("location_out_name", map.get("location_name"));
								if(ChdJson.validateJSON(String.valueOf(map.get("inva_date")))){
									mapDetailVo.put("inva_date", DateUtil.dateToString((Date)map.get("inva_date"), "yyyy-MM-dd"));
								}else{
									mapDetailVo.put("inva_date", map.get("inva_date"));
								}
								if(ChdJson.validateJSON(String.valueOf(map.get("disinfect_date")))){
									mapDetailVo.put("disinfect_date", DateUtil.dateToString((Date)map.get("disinfect_date"), "yyyy-MM-dd"));
								}else{
									mapDetailVo.put("disinfect_date", map.get("disinfect_date"));
								}
								/**---------------------数量计算---------------------*/
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
							/*材料主信息---------------end------------------------*/
							/*材料批次信息-------------begin----------------------*/
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
							
							//本批次数量充足直接出库
							if(amount <= imme_amount){
								mapDetailVo.put("amount", Double.valueOf(mapDetailVo.get("amount").toString()) + amount);
								mapDetailVo.put("sum_amount", Double.valueOf(mapDetailVo.get("sum_amount").toString()) + amount);
								inv_detail_data.append(",\"amount\":").append(amount)
									.append(",\"amount_money\":").append(amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = 0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								mapDetailVo.put("amount", Double.valueOf(mapDetailVo.get("amount").toString()) + imme_amount);
								mapDetailVo.put("sum_amount", Double.valueOf(mapDetailVo.get("sum_amount").toString()) + imme_amount);
								inv_detail_data.append(",\"amount\":").append(imme_amount)
									.append(",\"amount_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = NumberUtil.sub(amount, imme_amount);
							}
							/*材料批次信息-------------end------------------------*/
						}else{
							/*材料主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								inv_detail_data.substring(0, inv_detail_data.length()-1);
								inv_detail_data.append("]");
								mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
								detailList.add(mapDetailVo);
								break;
							}else{
								//库存信息累加
								mapDetailVo.put("cur_amount", Double.valueOf(mapDetailVo.get("cur_amount").toString())+Double.valueOf(map.get("cur_amount").toString()));
								mapDetailVo.put("imme_amount", Double.valueOf(mapDetailVo.get("imme_amount").toString())+Double.valueOf(map.get("imme_amount").toString()));
							}
							/*材料主信息---------------end----------------------*/
						}
						//记录批号条码信息
						batchBar = newBatchBar;
						isNot_frist = true;
					}
					if(batchBar.equals(newBatchBar)){
						inv_detail_data.substring(0, inv_detail_data.length()-1);
						inv_detail_data.append("]");
						mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
						detailList.add(mapDetailVo);
					}
				}else{
					if(!is_create_left){
						invEnoughMsg.append(mapDetailVo.get("inv_code")).append(" ").append(mapDetailVo.get("inv_name")).append("<br>");
					}
				}
			}
			
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存药品不足：<br>"+invEnoughMsg.toString()+"\"}";
			}
			if(detailList.size() > 0){
				return ChdJson.toJson(detailList);
			}else{
				return "{\"error\":\"所选数据材料库存不足！\"}";
			}
			
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成材料明细失败 数据库异常 请联系管理员! 方法 getInvJsonByInvList\"}";
		}
	}

	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	//@Override
	public String getAffiInvJsonByAffiInvListOld(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			//记录库存不足的材料
			StringBuffer invEnoughMsg = new StringBuffer();
			//是否不判断库存不足，如果是则库存剩余多少生成多少
			boolean is_create_left = false;
			//当前材料的出库数量
			double amount;
			//材料基础信息
			for(Map<String, Object> entityMap : entityList){
				is_create_left = false;
				Map<String,Object> mapDetailVo =new HashMap<String,Object>();
				amount = Double.parseDouble(String.valueOf(entityMap.get("amount")));
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				mapDetailVo.put("group_id", entityMap.get("group_id"));
				mapDetailVo.put("hos_id", entityMap.get("hos_id"));
				mapDetailVo.put("copy_code", entityMap.get("copy_code"));
				mapDetailVo.put("store_id", entityMap.get("store_id"));
				mapDetailVo.put("inv_id", entityMap.get("inv_id"));
				mapDetailVo.put("inv_no", entityMap.get("inv_no"));
				mapDetailVo.put("inv_code", entityMap.get("inv_code"));
				mapDetailVo.put("inv_name", entityMap.get("inv_name"));
				mapDetailVo.put("sup_id", entityMap.get("sup_id"));
				mapDetailVo.put("sup_no", entityMap.get("sup_no"));
				mapDetailVo.put("sup_name", entityMap.get("sup_name"));
				//用于科室申请
				if(entityMap.get("detail_id") != null){
					mapDetailVo.put("detail_id", entityMap.get("detail_id"));
				}
				if(entityMap.get("is_create_left") != null && "1".equals(entityMap.get("is_create_left").toString())){
					is_create_left = true;
				}
				
				StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				String batchBar = "";  //用于判断批号条码是否相同
				String newBatchBar = "";  //用于判断批号条码是否相同
				boolean isNot_frist = false;  //是否不是第一次循环
				//获取该材料各批次数据
				List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedAffiOutDetailInvList(mapDetailVo));
				if (list.size() > 0) {
					for (Map<String, Object> map : list) {
						newBatchBar = map.get("batch_no").toString() + map.get("bar_code").toString() + map.get("price").toString();
						
						//本批次材料剩余量
						double  imme_amount = Double.parseDouble(String.valueOf(map.get("imme_amount")));
						
						//材料是否已出完
						if(amount > 0 && imme_amount >0){
							/*材料主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								//如果该材料第二次出现批号条码不对应则需要添加新的明细记录
								if(isNot_frist){
									inv_detail_data.substring(0, inv_detail_data.length()-1);
									inv_detail_data.append("]");
									Map<String, Object> mapDetailVoOld = new HashMap<String, Object>();
									mapDetailVoOld.putAll(mapDetailVo);
									mapDetailVoOld.put("inv_detail_data", inv_detail_data.toString());
									detailList.add(mapDetailVoOld);
									inv_detail_data = new StringBuffer();
								}
								/**---------------------数量初始化--begin---------------------*/
								mapDetailVo.put("amount", 0);
								mapDetailVo.put("sum_amount", 0);
								mapDetailVo.put("cur_amount", 0);
								mapDetailVo.put("imma_amount", 0);
								/**---------------------数量初始化--end-----------------------*/
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
								//以下3个属性用于调拨
								mapDetailVo.put("location_out_id", map.get("location_id"));
								mapDetailVo.put("location_out_code", map.get("location_code"));
								mapDetailVo.put("location_out_name", map.get("location_name"));
								if(ChdJson.validateJSON(String.valueOf(map.get("inva_date")))){
									mapDetailVo.put("inva_date", DateUtil.dateToString((Date)map.get("inva_date"), "yyyy-MM-dd"));
								}else{
									mapDetailVo.put("inva_date", map.get("inva_date"));
								}
								if(ChdJson.validateJSON(String.valueOf(map.get("disinfect_date")))){
									mapDetailVo.put("disinfect_date", DateUtil.dateToString((Date)map.get("disinfect_date"), "yyyy-MM-dd"));
								}else{
									mapDetailVo.put("disinfect_date", map.get("disinfect_date"));
								}
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
							/*材料主信息---------------end------------------------*/
							/*材料批次信息-------------begin----------------------*/
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
							
							//本批次数量充足直接出库
							if(amount <= imme_amount){
								mapDetailVo.put("amount", Double.valueOf(mapDetailVo.get("amount").toString()) + amount);
								mapDetailVo.put("sum_amount", Double.valueOf(mapDetailVo.get("sum_amount").toString()) + amount);
								inv_detail_data.append(",\"amount\":").append(amount)
									.append(",\"amount_money\":").append(amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = 0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								mapDetailVo.put("amount", Double.valueOf(mapDetailVo.get("amount").toString()) + imme_amount);
								mapDetailVo.put("sum_amount", Double.valueOf(mapDetailVo.get("sum_amount").toString()) + imme_amount);
								inv_detail_data.append(",\"amount\":").append(imme_amount)
									.append(",\"amount_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = NumberUtil.sub(amount, imme_amount);
							}
							/*材料批次信息-------------end------------------------*/
						}else{
							/*材料主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								inv_detail_data.substring(0, inv_detail_data.length()-1);
								inv_detail_data.append("]");
								mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
								detailList.add(mapDetailVo);
								break;
							}else{
								//库存信息累加
								mapDetailVo.put("cur_amount", Double.valueOf(mapDetailVo.get("cur_amount").toString())+Double.valueOf(map.get("cur_amount").toString()));
								mapDetailVo.put("imme_amount", Double.valueOf(mapDetailVo.get("imme_amount").toString())+Double.valueOf(map.get("imme_amount").toString()));
							}
							/*材料主信息---------------end----------------------*/
						}
						//记录批号条码信息
						batchBar = newBatchBar;
						isNot_frist = true;
					}
					if(batchBar.equals(newBatchBar)){
						inv_detail_data.substring(0, inv_detail_data.length()-1);
						inv_detail_data.append("]");
						mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
						detailList.add(mapDetailVo);
					}
				}else{
					if(!is_create_left){
						invEnoughMsg.append(mapDetailVo.get("inv_code")).append(" ").append(mapDetailVo.get("inv_name")).append("<br>");
					}
				}
			}
			
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存药品不足：<br>"+invEnoughMsg.toString()+"\"}";
			}
			if(detailList.size() > 0){
				return ChdJson.toJson(detailList);
			}else{
				return "{\"error\":\"所选数据材料库存不足！\"}";
			}
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成材料明细失败 数据库异常 请联系管理员! 方法 getInvJsonByInvList\"}";
		}
	}

	@Override
	public String queryMedInvListDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedInvListDept(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedInvListDept(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryMedParas() throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String, Object>> list = medCommonMapper.queryMedParas(entityMap);
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(Map<String, Object> m : list){
			if("para_08005".equals(m.get("PARA_CODE")) || "para_08006".equals(m.get("PARA_CODE"))){
				if("0".equals(m.get("PARA_CODE").toString())){
					m.put("PARA_CODE", 2);
				}
			}
			map.put(m.get("PARA_CODE").toString(), m.get("PARA_VALUE"));
		}
		
		return map;
	}
	/**
	 * 代销出库材料列表
	 */
	@Override
	public String queryMedAffiOutInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedAffiOutInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedAffiOutInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 代销出库材料列表（有批次）
	 */
	@Override
	public String queryMedAffiOutDetailInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedAffiOutDetailInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedAffiOutDetailInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 先进先出组装材料批次数据
	 */
	@Override
	public String queryMedInvByFifo(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

			if(entityMap.containsKey("amount")){
				if(Integer.parseInt(entityMap.get("amount").toString()) > 0){
					entityMap.put("flag", "1");
				}else{
					entityMap.put("flag", "0");
				}
			}else{
				entityMap.put("flag", "1");
			}
			
			//fifo帐表信息
			List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>)medCommonMapper.queryMedStockInvDetailList(entityMap));
			Double amount = Double.valueOf(String.valueOf((entityMap.get("amount") == null ? 0 : entityMap.get("amount"))));
			Double imme_amount = null;
			int is_defeat = 1;
			if(amount < 0){
				is_defeat = -1; //需按先出先退生成出库单
			}
			amount = is_defeat * amount;
			//按先进先出生成出库单
			for(Map<String, Object> map : fifoList){
				
				if(is_defeat < 0){
					//当前批次已出库数量
					if("12".equals(entityMap.get("bus_type_code").toString()) || "10".equals(entityMap.get("bus_type_code").toString()) ||
						"16".equals(entityMap.get("bus_type_code").toString()) || "22".equals(entityMap.get("bus_type_code").toString()) ){
						imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
					}else{
						imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
					}
				}else{
					//当前批次即时库存
					imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
				}
				//判断当前批号批次是否充足
				if(amount <= imme_amount){
					map.put("amount", is_defeat * amount);
					//计算金额
					map.put("amount_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))));
					map.put("sale_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))));
					map.put("sell_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))));
					detailList.add(map);
					break;
				}else{
					//取当前批号批次数量并且申请单数量响应减少
					map.put("amount", is_defeat * imme_amount);		
					//计算金额
					map.put("amount_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))));
					map.put("sale_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))));
					map.put("sell_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))));
					detailList.add(map);			
					amount = NumberUtil.sub(amount, imme_amount);
				}
				//当数量为0，证明已经完成先进先出操作
				if(amount == 0){
					break;
				}
			}
			return ChdJson.toJson(detailList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 queryMedOutInvFifo\"}";
		}
	}
	
	/**
	 * 代销  先进先出组装材料批次数据
	 */
	@Override
	public String queryMedAffiInvByFifo(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			//fifo帐表信息
			List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>)medCommonMapper.queryMedAffiOutDetailInvList(entityMap));
			Double amount = Double.valueOf(String.valueOf((entityMap.get("amount") == null ? 0 : entityMap.get("amount"))));
			Double imme_amount = null;
			
			int is_defeat = 1;
			if(amount < 0){
				is_defeat = -1; //需按先出先退生成出库单
			}
			amount = is_defeat * amount;
			
			//按先进先出生成出库单
			for(Map<String, Object> map : fifoList){
				
				if(is_defeat < 0){
					//当前批次已出库数量
					imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
				}else{
					//当前批次即时库存
					imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
				}
				
				//判断当前批号批次是否充足
				if(amount <= imme_amount){
					map.put("amount", is_defeat * amount);
					//计算金额
					map.put("amount_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))));
					map.put("sale_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))));
					map.put("sell_money", is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))));
					detailList.add(map);
					break;
				}else{
					//取当前批号批次数量并且申请单数量响应减少
					map.put("amount", is_defeat * imme_amount);		
					//计算金额
					map.put("amount_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))));
					map.put("sale_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))));
					map.put("sell_money", is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))));
					detailList.add(map);			
					amount = NumberUtil.sub(amount, imme_amount);
				}
				//当数量为0，证明已经完成先进先出操作
				if(amount == 0){
					break;
				}
			}
			return ChdJson.toJson(detailList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
			//return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 queryMedOutInvFifo\"}";
		}
	}
	
	
	
	
	/**
	 * 取出当前物流的未结账最小会计期间
	 */
	@Override
	public Map<String, Object> queryMedCurYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = medCommonMapper.queryMedCurYearMonth(entityMap);
		
		return map;
			}
	/**
	 * 取出当前物流的最大结账期间（即：当前反结账期间）
	 */	
	@Override
	public Map<String, Object> queryMedLastYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map=new HashMap<String,Object>();
		map = medCommonMapper.queryMedLastYearMonth(entityMap);
		Map<String,Object> last_map = new HashMap<String,Object>();
		if(map==null || map.size()==0){

			last_map.put("last_year","");
			last_map.put("last_month","");
		}else{

			if(map.get("last_year") != null && !"".equals(map.get("last_year").toString())){
				last_map.put("last_year",map.get("last_year"));
				last_map.put("last_month",map.get("last_month"));
			}else{
				last_map.put("last_year","");
				last_map.put("last_month","");
			}
		}
		return last_map;
	}
	
	/**
	 * @Description 判断是否有不属于该仓库的材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMedStoreIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = medCommonMapper.existsMedStoreIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedStoreIncludeInv\"}";
		}	
	}
	
	/**
	 * @Description 判断是否有不属于该供应商的材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMedSupIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = medCommonMapper.existsMedSupIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedStoreIncludeInv\"}";
		}	
	}
	
	
	/**
	 * @Description 判断供应商是否是材料的唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMedInvOnlySup(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = medCommonMapper.existsMedInvOnlySup(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInvOnlySup\"}";
		}	
	}
	
	/**
	 * @Description 判断是否有不属于该供应商的材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMedAffiSupIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = medCommonMapper.existsMedAffiSupIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedStoreIncludeInv\"}";
		}	
	}
	/**
	 * @Description （代销）判断供应商是否是材料的唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMedAffiInvOnlySup(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = medCommonMapper.existsMedAffiInvOnlySup(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInvOnlySup\"}";
		}	
	}
	
	/**
	 * @Description 判断批号有效日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMedInvBatchInva(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inva_date = DateUtil.dateToString(medCommonMapper.queryMedInvBatchInva(entityMap), "yyyy-MM-dd");
			if(inva_date != null && !"".equals(inva_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("inva_date"))) && !inva_date.equals(DateUtil.jsDateToString(entityMap.get("inva_date").toString(), "yyyy-MM-dd"))){
				return "{\"state\":\"false\",\"inva_date\":\""+inva_date+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedInvBatchInva\"}";
		}	
		
		return "{\"state\":\"true\"}";
	}
	
	/**
	 * @Description 判断批号灭菌日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMedInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String disinfect_date = DateUtil.dateToString(medCommonMapper.queryMedInvBatchDisinfect(entityMap), "yyyy-MM-dd");
			if(disinfect_date != null && !"".equals(disinfect_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("disinfect_date"))) && !disinfect_date.equals(DateUtil.jsDateToString(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"))){
				return "{\"state\":\"false\",\"disinfect_date\":\""+disinfect_date+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedInvBatchDisinfect\"}";
		}	
		return "{\"state\":\"true\"}";
	}
	/**
	 * @Description 判断批号单价是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMedInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			Double price = null;
			if(entityMap.get("type") != null && "affi".equals(entityMap.get("type").toString().toLowerCase())){
				//代销
				price = medCommonMapper.queryMedAffiInvBatchPrice(entityMap);
			}else{
				//非代销
				price = medCommonMapper.queryMedInvBatchPrice(entityMap);
			}
			if(price != null && !"".equals(price.toString()) && ChdJson.validateJSON(String.valueOf(entityMap.get("price"))) && NumberUtil.sub(price, Double.valueOf(entityMap.get("price").toString())) != 0){
				return "{\"state\":\"false\",\"price\":\""+price+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedInvBatchPrice\"}";
		}	
		return "{\"state\":\"true\"}";
	}
	/**
	 * 生成table_code表的下一个单号（基本适用所有业务）
	 * @param entityMap
	 * @return
	 */
	@Override
	public String getMedNextNo(Map<String, Object> entityMap) throws DataAccessException{
		boolean ishave_store = true;
		boolean ishave_bus = true;
		boolean change_length = true;
		int length_no = 4;  //默认流水码长度为4位
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		//仓库ID为空则单据号不根据库房别名生成
		if(entityMap.get("store_id") == null || "".equals(entityMap.get("store_id").toString())){
			ishave_store = false;
		}
		//业务类型为空则单据号不根据业务类型简称生成
		if(entityMap.get("bus_type_code") == null || "".equals(entityMap.get("bus_type_code").toString())){
			ishave_bus = false;
		}
		if(entityMap.get("prefixe") == null){
			entityMap.put("prefixe", "");
		}
		if(entityMap.get("length_no") != null && !"".equals(entityMap.get("length_no").toString())){
			length_no = Integer.valueOf(entityMap.get("length_no").toString());
			change_length = false;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		map.put("bus_type_code", entityMap.get("bus_type_code"));

		//获取仓库别名store_alias
		String store_alias = "";
		if(ishave_store){
			store_alias = medCommonMapper.queryStoreAliasById(map);
		}
		//取业务类型简称type_flag
		String bus_type = "";
		if(ishave_bus){
			bus_type = medCommonMapper.queryBusTypeFlagByCode(map);
		}
		map.put("store_alias", store_alias);
		map.put("bus_type", bus_type);
		map.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("day", entityMap.get("day"));
		map.put("prefixe", entityMap.get("prefixe"));
		
		//校验是否按日期生成单号
		String containsDay; 
		if(entityMap.get("containsDay") != null && !"".equals(entityMap.get("containsDay"))){
			containsDay = entityMap.get("containsDay").toString();
		}else{
			map.put("para_code", "08037");
			containsDay = medCommonMapper.getMedAccPara(map);
		}
		if("0".equals(containsDay)){
			//不按日生成单据号则Map中移除日期并单据流水长度+2
			//由于本月存在流水码数据如果取消日期会造成查询出多条数据，所以这里用00代替日期
			map.put("day", "00");
			//移除它是为了组装单号时方便判断
			entityMap.remove("day");
			if(change_length){
				length_no = length_no + 2;
			}
		}
		
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
		for (int i = max_no.length() ; i < length_no; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = "";
		if(ishave_store){
			if(ishave_bus){
				//有业务类型的由于单据号过长所有年份取后两位
				next_no = store_alias + "-" + bus_type + entityMap.get("year").toString().substring(2, 4) + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
			}else{
				next_no = store_alias + "-" + entityMap.get("year").toString() + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
			}
		}else{
			next_no = entityMap.get("prefixe").toString() + entityMap.get("year") + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
		}
		
		return next_no;
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

	@Override
	public String queryInvPicture(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = medCommonMapper.queryInvPicture(entityMap);
			
		return ChdJson.toJson(list);
	}

	
	/**
	 * 采购计划  材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedInvListByPur(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = medCommonMapper.queryMedInvListByPur(entityMap);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medCommonMapper.queryMedInvListByPur(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
	}
	
	@Override
	public String queryMedCertDate(Map<String,Object> entityMap) throws DataAccessException{
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date() ;
			entityMap.put("currentDate", df.format(currentDate));
			Map<String,Object> certDate = medCommonMapper.queryMedCertDate(entityMap);
			//没有到期日期的证件终身有效
			if(certDate != null && certDate.get("end_date") != null && currentDate.after(df.parse(String.valueOf(certDate.get("end_date"))))){
				return "{\"state\":\"false\",\"end_date\":\""+df.format(certDate.get("end_date"))+"\",\"days\":\""+certDate.get("days")+"\"}";
			}else{
				return "{\"state\":\"true\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";
		}	
	}
	
	@Override
	public Map<String, Object> getLoginUserMsg(Map<String,Object> entityMap) throws DataAccessException{
		
		return JsonListMapUtil.toMapLower(medCommonMapper.queryLoginUserMsg(entityMap));
	}

	@Override
	public String queryMedInvListIn(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedInvListIn(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedInvListIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
   }

	/**
	 * 材料期初入库
	 */
	@Override
	public String queryMedInvListInitial(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedInvListInitial(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedInvListInitial(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
   }
	
	
	@Override
	public String existsMedAffiSupIsInv(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = medCommonMapper.existsMedAffiSupIsInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInvOnlySup\"}";
		}
	}

	@Override
	public String existsMedInSupIsInv(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = medCommonMapper.existsMedInSupIsInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInvOnlySup\"}";
		}
	}
	
	/**
	 * @Description 耐用品库房材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedDuraStoreInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedDuraStoreInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medCommonMapper.queryMedDuraDeptInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medCommonMapper.queryMedDuraDeptInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 普通材料扫码出库返回对应材料列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException{
		String flag = "1";
		if(entityMap.get("flag") != null && "".equals(entityMap.get("flag").toString())){
			flag = entityMap.get("flag").toString();
		}
		
		if("1".equals(flag)){
			//获取材料ID（检验条码是否存在）
			Long inv_id = medCommonMapper.existsMedOutInvListByBar(entityMap);
			if(inv_id != null){
				entityMap.put("inv_id", inv_id);
				List<Map<String, Object>> list = medCommonMapper.queryMedOutInvListByBar(entityMap);
				return "{\"state\":\"true\",\"invData\":"+ChdJson.toJsonLower(list)+"}";
			}else{
				return "{\"state\":\"false\",\"warn\":\"条码"+entityMap.get("bar_code").toString()+"不存在或不在该库房！\"}";
			}
		}else if("2".equals(flag)){
			//用于验证
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("store_id", entityMap.get("store_id"));
			map.put("bar_code", entityMap.get("bar_code"));
			
			//获取开始条码对应的材料ID（检验条码是否存在）
			Long inv_id = medCommonMapper.existsMedOutInvListByBar(map);
			if(inv_id != null){
				//获取结束条码对应的材料ID（检验条码是否存在）
				map.put("store_id", entityMap.get("bar_code_end"));
				Long inv_id_end = medCommonMapper.existsMedOutInvListByBar(map);
				if(inv_id_end != null){
					//校验开始条码与结束条码对应的材料是否一致
					if(inv_id == inv_id_end){
						entityMap.put("inv_id", inv_id);
						List<Map<String, Object>> list = medCommonMapper.queryMedOutInvListByBar(entityMap);
						return "{\"state\":\"true\",\"invData\":"+ChdJson.toJsonLower(list)+"}";
					}else{
						return "{\"state\":\"false\",\"error\":\"开始条码与结束条码对应的材料不一致！\"}";
					}
				}else{
					return "{\"state\":\"false\",\"warn\":\"条码"+map.get("bar_code").toString()+"不存在或不在该库房！\"}";
				}
			}else{
				return "{\"state\":\"false\",\"warn\":\"条码"+map.get("bar_code").toString()+"不存在或不在该库房！\"}";
			}
		}
		return "{\"state\":\"false\",\"error\":\"flag参数失效，请联系管理员！\"}";
	}

	/**
	 * @Description 代销材料扫码出库返回对应材料列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedAffiOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException{
		String flag = "1";
		if(entityMap.get("flag") != null && "".equals(entityMap.get("flag").toString())){
			flag = entityMap.get("flag").toString();
		}
		
		if("1".equals(flag)){
			//获取材料ID（检验条码是否存在）
			Long inv_id = medCommonMapper.existsMedAffiOutInvListByBar(entityMap);
			if(inv_id != null){
				entityMap.put("inv_id", inv_id);
				List<Map<String, Object>> list = medCommonMapper.queryMedAffiOutInvListByBar(entityMap);
				return "{\"state\":\"true\",\"invData\":"+ChdJson.toJsonLower(list)+"}";
			}else{
				return "{\"state\":\"false\",\"warn\":\"条码"+entityMap.get("bar_code").toString()+"不存在或不在该库房！\"}";
			}
		}else if("2".equals(flag)){
			//用于验证
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("store_id", entityMap.get("store_id"));
			map.put("bar_code", entityMap.get("bar_code"));
			
			//获取开始条码对应的材料ID（检验条码是否存在）
			Long inv_id = medCommonMapper.existsMedAffiOutInvListByBar(map);
			if(inv_id != null){
				//获取结束条码对应的材料ID（检验条码是否存在）
				map.put("store_id", entityMap.get("bar_code_end"));
				Long inv_id_end = medCommonMapper.existsMedAffiOutInvListByBar(map);
				if(inv_id_end != null){
					//校验开始条码与结束条码对应的材料是否一致
					if(inv_id == inv_id_end){
						entityMap.put("inv_id", inv_id);
						List<Map<String, Object>> list = medCommonMapper.queryMedAffiOutInvListByBar(entityMap);
						return "{\"state\":\"true\",\"invData\":"+ChdJson.toJsonLower(list)+"}";
					}else{
						return "{\"state\":\"false\",\"error\":\"开始条码与结束条码对应的材料不一致！\"}";
					}
				}else{
					return "{\"state\":\"false\",\"warn\":\"条码"+map.get("bar_code").toString()+"不存在或不在该库房！\"}";
				}
			}else{
				return "{\"state\":\"false\",\"warn\":\"条码"+map.get("bar_code").toString()+"不存在或不在该库房！\"}";
			}
		}
		return "{\"state\":\"false\",\"error\":\"flag参数失效，请联系管理员！\"}";
	}
	
	/**
	 * 材料退货查询材料列表
	 */
	@Override
	public String queryMedStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListBackNew(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedStockInvListBackNew(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 普通材料退库查询科室已领用材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedDeptStockInvList(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonMapper.queryMedDeptStockInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	public String queryMedStoreByCode(Map<String, Object> mapVo)
			throws DataAccessException {
		
		try {	
			Map<String, Object> map=medStoreMapper.queryByCode(mapVo);
			if(map == null || "".equals(map)){
				return "{\"state\":\"true\",\"is_control\":\"0\"}";
			}else{
				return "{\"state\":\"true\",\"is_control\":\""+map.get("is_control").toString()+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedStoreByCode\"}";
		}
		
		
	}
	
	
}
