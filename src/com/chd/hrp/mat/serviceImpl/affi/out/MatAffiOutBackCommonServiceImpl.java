package com.chd.hrp.mat.serviceImpl.affi.out;

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
import com.chd.hrp.mat.dao.affi.in.MatAffiInCommonMapper;
import com.chd.hrp.mat.dao.affi.out.MatAffiOutBackCommonMapper; 
import com.chd.hrp.mat.dao.base.MatAffiBalanceMapper;
import com.chd.hrp.mat.dao.base.MatAffiBatchMapper;
import com.chd.hrp.mat.dao.base.MatAffiFifoMapper;
import com.chd.hrp.mat.dao.base.MatAffiInvHoldMapper;
import com.chd.hrp.mat.dao.base.MatAffiOutBackMapper; 
import com.chd.hrp.mat.dao.base.MatApplyOutRelaMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.service.affi.out.MatAffiOutBackCommonService;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.github.pagehelper.PageInfo;

@Service("matAffiOutBackCommonService")
public class MatAffiOutBackCommonServiceImpl implements MatAffiOutBackCommonService{
	private static Logger logger = Logger.getLogger(MatAffiOutBackCommonServiceImpl.class);
	//引入dao
	@Resource(name = "matAffiOutBackMapper")
	private final MatAffiOutBackMapper matAffiOutBackMapper = null;
	
	@Resource(name = "matAffiOutBackCommonMapper")
	private final MatAffiOutBackCommonMapper matAffiOutBackCommonMapper = null;
	 
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
	
	@Resource(name = "matAffiFifoMapper")
	private final MatAffiFifoMapper matAffiFifoMapper = null;
	
	@Resource(name = "matAffiInvHoldMapper")
	private final MatAffiInvHoldMapper matAffiInvHoldMapper = null;
	
	@Resource(name = "matAffiBatchMapper")
	private final MatAffiBatchMapper matAffiBatchMapper = null;
	
	@Resource(name = "matAffiBalanceMapper")
	private final MatAffiBalanceMapper matAffiBalanceMapper = null;
	
	@Resource(name = "matApplyOutRelaMapper")
	private final MatApplyOutRelaMapper matApplyOutRelaMapper = null;
	
	/**
	 * 代销出库主表
	 * @param mapVo
	 * @return
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

		if (mapVo.get("his_flag") == null) {
			mapVo.put("his_flag", "");
		}
		
		if (mapVo.get("use_state") == null) {
			mapVo.put("use_state", "0");
		}
		
		if (mapVo.get("is_dir") == null) {
			mapVo.put("is_dir", "0");
		}
		
		if (mapVo.get("proj_id") == null) {
			mapVo.put("proj_id", "");
		}
		
		return mapVo;
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {
		Map<String, Object> detailMap = new HashMap<String, Object>();
			detailMap.put("group_id", 0);
			detailMap.put("hos_id", 0);
			detailMap.put("copy_code", "");
			detailMap.put("stroe_id", "");
			detailMap.put("price", "0");
			detailMap.put("amount", "0");
			detailMap.put("sale_price", "0");
			detailMap.put("sale_money", "0");
			detailMap.put("sell_price", "0");
			detailMap.put("sell_money", "0");
			detailMap.put("allot_price", "0");
			detailMap.put("allot_money", "0");
			detailMap.put("pack_code", "");
			detailMap.put("num_exchange", "0");
			detailMap.put("pack_price", "0");
			detailMap.put("num", "0");
			detailMap.put("inva_date", "");
			detailMap.put("disinfect_date", "");
			detailMap.put("location_id", 0);
			detailMap.put("note", "");
			return detailMap;
	}
	
	//验证
	public boolean validateJSON(String str) {
		if (str != null && str != "null" && str != "" && str != "0") {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @Description 
	 * 查询结果集 代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
//		if(entityMap.get("out_dateB") != null && !"".equals(entityMap.get("out_dateB"))){
//			entityMap.put("out_dateB", DateUtil.stringToDate(entityMap.get("out_dateB").toString(), "yyyy-MM-dd"));
//		}
//		if(entityMap.get("out_dateE") != null && !"".equals(entityMap.get("out_dateE"))){
//			entityMap.put("out_dateE", DateUtil.stringToDate(entityMap.get("out_dateE").toString(), "yyyy-MM-dd"));
//		}
//		if(entityMap.get("confirm_dateB") != null && !"".equals(entityMap.get("confirm_dateB"))){
//			entityMap.put("confirm_dateB", DateUtil.stringToDate(entityMap.get("confirm_dateB").toString(), "yyyy-MM-dd"));
//		}
//		if(entityMap.get("confirm_dateE") != null && !"".equals(entityMap.get("confirm_dateE"))){
//			entityMap.put("confirm_dateE", DateUtil.stringToDate(entityMap.get("confirm_dateE").toString(), "yyyy-MM-dd"));
//		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<?> list = matAffiOutBackCommonMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matAffiOutBackCommonMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	
	/**
	 * @Description 
	 * 代销代销出库--添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			
			//转换日期格式
			if(entityMap.get("out_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"入库日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("out_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("out_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("out_date") != null && !"".equals(entityMap.get("out_date"))){
				entityMap.put("out_date", DateUtil.stringToDate(entityMap.get("out_date").toString(), "yyyy-MM-dd"));
			}
			
			//(1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！
			flag = matCommonMapper.existsMatYearMonthIsAccount(entityMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能保存！\",\"state\":\"false\"}";
			}
			
			entityMap = defaultValue(entityMap);
			//取出主表ID（自增序列）
			entityMap.put("out_id", matAffiOutBackMapper.queryMatAffiOutMainSeq());
			//自动生成代销代销出库单据号
			entityMap.put("table_code", "MAT_AFFI_OUT");
			entityMap.put("out_no", matCommonService.getMatNextNo(entityMap));
			
			//保存明细数据
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			StringBuffer invEnoughMsg = new StringBuffer(); 
			
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){	
						Map<String,Object> detailMap = defaultDetailValue();
						
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("out_id", entityMap.get("out_id").toString());//主表ID
						detailMap.put("out_no", entityMap.get("out_no").toString());//主表no
						
						//用于批次查询
						detailMap.put("store_id", entityMap.get("store_id").toString());
						detailMap.put("bus_type_code", entityMap.get("bus_type_code").toString());
						//不存在材料ID视为空行
						if (validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
							detailMap.put("inv_id",  jsonObj.get("inv_id").toString());//材料ID
							detailMap.put("inv_no",  jsonObj.get("inv_no").toString());//材料NO
							
							/*材料主信息-----------------begin---------------------*/
							if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
								detailMap.put("batch_no", jsonObj.get("batch_no").toString());
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
								detailMap.put("bar_code", jsonObj.get("bar_code").toString());
							}else{
								detailMap.put("bar_code", null);
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("price")))) {
								detailMap.put("price", jsonObj.get("price").toString());
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
								detailMap.put("sale_price", jsonObj.get("sale_price").toString());
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
								detailMap.put("sell_price", jsonObj.get("sell_price").toString());
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
								detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
								detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
								detailMap.put("location_id", jsonObj.get("location_id").toString());
							}else{
								detailMap.put("location_id", "0");
							}
							
							if (validateJSON(String.valueOf(jsonObj.get("note")))) {
								detailMap.put("note", jsonObj.get("note").toString());
							}
							if (validateJSON(String.valueOf(jsonObj.get("sup_id")))) {
								detailMap.put("sup_id", jsonObj.get("sup_id"));
							}
							if (validateJSON(String.valueOf(jsonObj.get("sup_no")))) {
								detailMap.put("sup_no", jsonObj.get("sup_no"));
							}
							/*材料主信息-----------------end---------------------*/
							
							/*材料批次信息---------------begin---------------------*/
							//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
							if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount")))&& validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")
							).equals(String.valueOf(jsonObj.get("amount")))){
								
								for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
									//没材料ID视为空行
									if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
										Map<String, Object> detailNewMap = new HashMap<String, Object>();
										detailNewMap.putAll(detailMap);
										detailNewMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
										if (validateJSON(String.valueOf(m.get("batch_sn")))) {
											detailNewMap.put("batch_sn", m.get("batch_sn").toString());
										}
										if (validateJSON(String.valueOf(m.get("amount")))) {
											detailNewMap.put("amount", m.get("amount").toString());
										}
										if (validateJSON(String.valueOf(m.get("amount_money")))) {
											detailNewMap.put("amount_money", m.get("amount_money").toString());
										}
										if (validateJSON(String.valueOf(m.get("sale_money")))) {
											detailNewMap.put("sale_money", m.get("sale_money").toString());
										}
										if (validateJSON(String.valueOf(m.get("sell_money")))) {
											detailNewMap.put("sell_money", m.get("sell_money").toString());
										}
										detailList.add(detailNewMap);
									}
								}
							}else{
								//按先进先出匹配批次信息
								List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(detailMap));
								Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
								Double imme_amount = null;

								int is_defeat = 1;
								if(amount < 0){
									is_defeat = -1; //需按先出先退生成出库单
								}
								amount = is_defeat * amount;
								
								//按先进先出生成出库单
								for(Map<String, Object> map : fifoList){
									Map<String, Object> detailNewMap = new HashMap<String, Object>();
									detailNewMap.putAll(detailMap);
									detailNewMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
									detailNewMap.put("batch_sn", map.get("batch_sn").toString());
									
									if(is_defeat < 0){
										//当前批次已出库数量
										imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
									}else{
										//当前批次即时库存
										imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
									}
									
									//判断当前批号批次是否充足
									if(amount <= imme_amount){
										detailNewMap.put("amount", String.valueOf(is_defeat * amount));
										//计算金额
										detailNewMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
										detailNewMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
										detailNewMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
										detailNewMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
										
										detailList.add(detailNewMap);
										amount = 0.0;
										break;
									}else{
										//取当前批号批次数量并且申请单数量响应减少
										detailNewMap.put("amount", String.valueOf(is_defeat * imme_amount));		
										//计算金额
										detailNewMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
										detailNewMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
										detailNewMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
										detailNewMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
										
										detailList.add(detailNewMap);			
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
							
							/*材料批次信息---------------end---------------------*/
						}
					}	
				}
				if(detailList.size() <= 0){
					return "{\"error\":\"出库单明细数据为空!\"}";
				}
				if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
					return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
				}
				//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
				String invEnough = matCommonMapper.existsMatAffiInvIsEnough(detailList);
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
				}
				//新增入库主表数据
				matAffiOutBackMapper.addMatAffiOutBackMain(entityMap);
				//新增入库明细数据
				ChdJson.toJson(detailList);
				matAffiOutBackMapper.addMatAffiOutBackDetail(detailList);
				
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			return "{\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("out_id").toString()+","
				+"\"}";
		}catch(DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		
	}
	/**
	 * @Description 
	 * 批量添加 代销代销出库<BR> 
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
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatAffiOutCommon\"}";
		}
	}
	/**
	 * @Description 
	 * 修改页面--根据主键查询明细信息
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBackByCodeDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matAffiOutBackMapper.queryBackByCodeDetail(entityMap);
		return ChdJson.toJson(list);
		
	}
	/**
	 * @Description 
	 * 代销代销出库  修改<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			// 保存主表数据
			entityMap = defaultValue(entityMap);
			String out_date = (String) entityMap.get("out_date");
			//entityMap.put("year", out_date.substring(0, 4));
			//entityMap.put("month", out_date.substring(5, 7));			
			entityMap.put("day", out_date.substring(8, 10));
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("out_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			int flag = 1;			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);			
			if(flag == 0){				
				return "{\"error\":\"修改失败，所选期间不存在请配置！\",\"state\":\"false\"}";				
			}			
			//(2)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！			
			flag = matCommonMapper.existsMatYearMonthIsAccount(entityMap);
			if(flag != 0){				
				return "{\"error\":\"修改失败，当月已结账不能保存！\",\"state\":\"false\"}";				
			}
			if(entityMap.get("out_date") != null && !"".equals(entityMap.get("out_date"))){
				entityMap.put("out_date", DateUtil.stringToDate(entityMap.get("out_date").toString(), "yyyy-MM-dd"));
			}
			
			// 保存明细数据
			List<Map<String, Object>> out_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			//System.out.println("************* out_detail_data:"+entityMap.get("out_detail_data"));
			StringBuffer invEnoughMsg = new StringBuffer();
			JSONArray json = JSONArray.parseArray((String)entityMap.get("out_detail_data"));
			Iterator it = json.iterator();			
			while (it.hasNext()) {
				Map<String, Object> mapDetailVo = defaultDetailValue();
				
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("out_id", entityMap.get("out_id").toString());
				mapDetailVo.put("out_no", entityMap.get("out_no").toString());
				mapDetailVo.put("store_id", entityMap.get("store_id").toString());
				mapDetailVo.put("bus_type_code", entityMap.get("bus_type_code").toString());
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", null);
					}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {mapDetailVo.put("price", jsonObj.get("price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {mapDetailVo.put("amount", jsonObj.get("amount").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sell_money")))) {mapDetailVo.put("sell_money", jsonObj.get("sell_money").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("amount_money")))) {				
						mapDetailVo.put("amount_money", jsonObj.get("amount_money").toString());	
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
					if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sup_id")))) {
						mapDetailVo.put("sup_id", jsonObj.get("sup_id"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("sup_no")))) {
						mapDetailVo.put("sup_no", jsonObj.get("sup_no"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}
					//System.out.println("************* inv_detail_data:"+jsonObj.get("inv_detail_data").toString());
					/*材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						//System.out.println("!!!!!!!!!!!!");
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								if (validateJSON(String.valueOf(m.get("detail_id")))) {
									detailMap.put("detail_id", m.get("detail_id").toString());
								}else{
									detailMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
								}
								
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {
									detailMap.put("batch_sn", m.get("batch_sn").toString());
								}
								
								if (validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								out_detail_batch.add(detailMap);
							}
					    }
						
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(mapDetailVo));
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
							detailMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
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
					/*材料批次信息---------------end-----------------------*/
				}
				
			}
			if(out_detail_batch.size() <= 0){
				
				return "{\"error\":\"出库单明细数据为空!\"}";
			}
			
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				
				return "{\"error\":\""+invEnoughMsg.substring(0, invEnoughMsg.length()-1).toString()+"库存物资不足!\"}";
			}
			
			//判断即时库存是否充足
			String invEnough = matCommonMapper.existsMatAffiInvIsEnough(out_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){

				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			matAffiOutBackMapper.updateMatAffiOutMain(entityMap);// 保存主表
			matAffiOutBackMapper.deleteMatAffiOutDetail(entityMap);//删除明细
			matAffiOutBackMapper.addMatAffiOutBackDetail(out_detail_batch);// 保存明细表

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	
	
	/**
	 * @Description 
	 * 批量修改 代销代销出库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 updateBatchMatAffiOutCommon\"}";
		}
	}

	/**
	 * 配套导入 查询明细 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAffiOutMatchDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		return ChdJson.toJson(matAffiOutBackCommonMapper.queryMatAffiOutMatchDetail(entityMap));
		
	}
	
	/**
	 * 配套导入--组装明细数据
	 */
	@Override
	public String queryMatAffiOutDetailByMatch(Map<String, Object> entityMap) throws DataAccessException {
	try{
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
			//出库数量 = 套数 * 配套表材料数量
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
			//获取该材料各批次数据
			List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(mapDetailVo));
			if (list.size() > 0) {
				for (Map<String, Object> map : list) {
					newBatchBar = map.get("batch_no") == null ? "" : map.get("batch_no").toString() + map.get("bar_code") == null ? "" : map.get("bar_code").toString();
					//材料是否已出完
					if(amount > 0){
						/*材料主信息---------------begin----------------------*/
						if(!batchBar.equals(newBatchBar)){
							mapDetailVo.put("inv_no", map.get("inv_no"));
							mapDetailVo.put("inv_code", map.get("inv_code"));
							mapDetailVo.put("inv_name", map.get("inv_name"));
							mapDetailVo.put("inv_model", map.get("inv_model"));
							mapDetailVo.put("unit_name", map.get("unit_name")); 
							mapDetailVo.put("mat_type_name", map.get("mat_type_name"));
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
						
						//本批次材料剩余量
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
						/*材料批次信息-------------end------------------------*/
					}else{
						/*材料主信息---------------begin----------------------*/
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
						/*材料主信息---------------end----------------------*/
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
				msg.append(mapDetailVo.get("inv_code")).append(" ").append(mapDetailVo.get("inv_name")).append("材料库存不足").append("<br>");
			}
		}
		
		//返回库存不足的材料
		if(msg.toString().length() > 0){
			return "{\"error\":\""+msg.toString()+"\"}";
		}
		return ChdJson.toJsonLower(detailData);
	}catch (DataAccessException e) {
		logger.error(e.getMessage(), e);
		throw new SysException("{\"error\":\"操作失败\"}");
		//return "{\"error\":\"配套导入失败 数据库异常 请联系管理员! 方法 queryMatOutDetailByMatch\"}";
	}
}
	
	/**
	 * 整单出库  查询主表 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiInMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryAffiInMain(entityMap);
			return ChdJson.toJsonLower(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryAffiInMain(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	/**
	 * 整单出库  查询明细材料 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiInInv(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryAffiInInv(entityMap);
			return ChdJson.toJsonLower(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryAffiInInv(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());

		}
		
	}
	
	/**
	 * 整单出库  组装要添加的明细数据 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiInWholeDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)matAffiOutBackCommonMapper.queryAffiInWholeDetail(entityMap);
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 审核 代销出库单据 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String auditMatAffiOutBackCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//1、状态不为新建不能审核，从页面判断
			//2、修改状态 state = 2 ,添加审核人，审核日期
			matAffiOutBackCommonMapper.auditMatAffiOutBackCommon(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMatAffiOutCommon\"}";
		}
		
	}

	/**
	 * 取消审核 代销出库单据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMatAffiOutBackCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//1、状态不为新建不能审核，从页面判断
			//2、修改状态 state =1 ,审核人，审核日期置为空
			matAffiOutBackCommonMapper.unAuditMatAffiOutBackCommon(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"销审失败 数据库异常 请联系管理员! 方法 unAuditMatAffiOutCommon\"}";
		}	
	}
	
	/**
	 * 删除 代销出库单据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteMatAffiOutBackCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//1、判断若单据state>1，不允许删除，页面判断
			//2、根据参数04011判断是否倒序删除，如果是，则只能删除单据号最大的出库单。否则，医院人为控制单据连续性。
			
			//3、判断当前登录用户是否为制单人，非制单人不允许删除
			Map<String,Object> mapVo = new HashMap<String,Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("user_id", entityList.get(0).get("user_id"));
			StringBuffer out_ids =  new StringBuffer(); 
			for(int a = 0;a < entityList.size();a++){
				out_ids.append(entityList.get(a).get("out_id")).append(',');
			}
			mapVo.put("out_ids", out_ids.substring(0,out_ids.length()-1));//明细IDS
			
			int flag = matAffiOutBackMapper.queryBackNotByUserId(mapVo);
			if(flag > 0 ){
				return "{\"error\":\"删除失败 ,非制单人不允许删除单据!\"}";
			}
			//7、删除申请单与出库单对应关系
			matApplyOutRelaMapper.deleteMatApplyOutRelaBatch(entityList);
			//4、若是整单出库的单据，需要删除对应关系表
			//	matAffiOutBackMapper.deleteBatchMatAffiTranRela(mapVo);
			
			//5、删除明细表
				matAffiOutBackMapper.deleteBatchMatAffiOutBackDetail(entityList);
			//6、删除主表
				matAffiOutBackMapper.deleteBatchMatAffiOutBackMain(entityList);
	
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatAffiOutCommon\"}";
		}	
	}
	
	/**
	 * 代销出库 查询材料即时库存
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAffiOutByInvHold(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByInvHold(entityMap);
			return ChdJson.toJsonLower(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByInvHold(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}		
	}
	
	/**
	 * 代销出库 选择材料--查询 mat_affi_fifo 查有批次结存表
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAffiOutByFifoBalance(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");	
		if (sysPage.getTotal() == -1) {	
			String flag = String.valueOf(entityMap.get("flag"));

			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();	
			
			if (flag!=null && "fifo".equals(flag)) {//没有选择明细数据 走先进先出
				List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByFifoBalance(entityMap);	
				if (list.size() > 0) {
					double outAmountCout = 0;// 统计先进先出的数据的总数 后台通过查询获取的数据累计相加
					double amount = Double.parseDouble(String.valueOf(entityMap.get("amount")));//当前数据的出库数量			
					for (Map<String, Object> map : list) {
						Map viewMap = new HashMap();
						// 首先转换成小写的KEY
						for (String key : map.keySet()) {viewMap.put(key, map.get(key));}
						//for (String key : map.keySet()) {viewMap.put(key.toLowerCase(), map.get(key));}					
						double  instant_amount = Double.parseDouble(String.valueOf(viewMap.get("instant_amount")));						
						outAmountCout = outAmountCout + instant_amount;						
						viewMap.put("amount", viewMap.get("instant_amount"));					
						 if(NumberUtil.sub(outAmountCout, amount)  >=0 ){							 
							 //viewMap.put("left_amount", outAmountCout - amount);							 
							 viewMap.put("amount", NumberUtil.sub(instant_amount, (NumberUtil.sub(outAmountCout, amount))));
							 returnList.add(viewMap);						 
							 break;					
						 }						 
						 viewMap.put("amount", instant_amount);						
						 returnList.add(viewMap);
					}
				}				
				return ChdJson.toJsonLower(returnList);
				
			}else if("matched".equals(flag)){
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
						
						List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByFifoBalance(mapDetailVo);

						if (list.size() > 0) {
							double outAmountCout = 0;// 统计先进先出的数据的总数 后台通过查询获取的数据累计相加
							double amount = Double.parseDouble(String.valueOf(jsonObj.get("amount")));//当前数据的出库数量							
							for (Map<String, Object> map : list) {
								Map viewMap = new HashMap();
								// 首先转换成小写的KEY
								for (String key : map.keySet()) {viewMap.put(key, map.get(key));}
								//for (String key : map.keySet()) {viewMap.put(key.toLowerCase(), map.get(key));}								
								double  instant_amount = Double.parseDouble(String.valueOf(viewMap.get("instant_amount")));								
								outAmountCout = outAmountCout + instant_amount;								
								viewMap.put("amount", viewMap.get("instant_amount"));								
								 if(NumberUtil.sub(outAmountCout, amount)  >=0 ){									 
									 //viewMap.put("left_amount", outAmountCout - amount);									 
									 viewMap.put("amount", NumberUtil.sub(instant_amount, (NumberUtil.sub(outAmountCout, amount))));
									 returnList.add(viewMap);									 
									 break;								
								 }								 
								viewMap.put("amount", instant_amount);
								returnList.add(viewMap);
							}
						}						
					}					
					return ChdJson.toJsonLower(returnList);					
				} catch (DataAccessException e) {
					e.printStackTrace();
				}				
			}else{
				
				List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByFifoBalance(entityMap);				
				return ChdJson.toJsonLower(list);
			
			}
			
			return "";
		}else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackCommonMapper.queryMatAffiOutByFifoBalance(entityMap,rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
		
	}
	
	/**
	 * @Description 
	 * 复制  出库单据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String copyMatAffiOutBackCommon(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			//1、只能复制入库确认状态的单据，从页面判断
			//2、循环entityList  查询主表信息，从表信息，资金来源表信息  然后插入
			List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放mat_out_main字段			
			List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放mat_out_detail字段		
			Map<String, List<Map<String, Object>>> fifoMap = new HashMap<String, List<Map<String,Object>>>(); //存放材料批次信息	

			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			Integer is_dir = 0;
			String fifoKey = "";
			StringBuffer invEnoughMsg = new StringBuffer(); 
			
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String[] dates = date.split("-");
			String user_id = SessionManager.getUserId();
			
			List<Map<String, Object>> listVo = entityList;			
			for(Map<String, Object> tmp : entityList){				
				Map<String, Object> mom = (Map<String, Object>) matAffiOutBackMapper.queryByCode(tmp);
				
				Map<String, Object> mainMap = defaultBeanToMap(mom);
				mainMap.put("out_date", date);
				mainMap.put("year", dates[0]);
				mainMap.put("month", dates[1]);
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("table_code", "MAT_AFFI_OUT");
				mainMap.put("out_no", matCommonService.getMatNextNo(mainMap));					
				mainMap.put("out_id",matAffiOutBackMapper.queryMatAffiOutMainSeq());
				mainMap.put("state", "1");
				mainMap.put("is_dir", "0");
				mainMap.put("maker", user_id);
				if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date"))){
					mainMap.put("out_date", DateUtil.stringToDate(mainMap.get("out_date").toString(), "yyyy-MM-dd"));
				}
				mainMap.put("checker", "");
				mainMap.put("check_date", "");				
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
				mainMap.put("brief", "复制"+mom.get("out_no")+"出库单");				
				mainList.add(mainMap);				
			
				//查询明细表
				List<Map<String, Object>> list_detail = (List<Map<String, Object>>)matAffiOutBackMapper.queryBackByCodeDetail(tmp);
				for(Map<String, Object> mod : list_detail){					
					Map<String, Object> mapDetailVo = detailBeanToMap(mod);					
					mapDetailVo.put("out_id", mainMap.get("out_id").toString());
					mapDetailVo.put("out_no", mainMap.get("out_no").toString());	
					mapDetailVo.put("store_id", mainMap.get("store_id"));  //用于库存查询
					mapDetailVo.put("bus_type_code", mainMap.get("bus_type_code"));  //用于库存查询
					
					if(mod.get("inva_date") != null && !"".equals(mod.get("inva_date"))){
						mapDetailVo.put("inva_date", DateUtil.stringToDate(mod.get("inva_date").toString(), "yyyy-MM-dd"));
					}
					if(mod.get("disinfect_date") != null && !"".equals(mod.get("disinfect_date"))){
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(mod.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}
					if(mod.get("sup_id") != null && !"".equals(mod.get("sup_id"))){
						mapDetailVo.put("sup_id", mod.get("sup_id"));
					}
					if(mod.get("sup_no") != null && !"".equals(mod.get("sup_no"))){
						mapDetailVo.put("sup_no", mod.get("sup_no"));
					}
					
					/**********************按先进先出匹配批次信息************************/
					fifoKey = mapDetailVo.get("inv_id").toString() + mapDetailVo.get("batch_no").toString() + mapDetailVo.get("bar_code").toString() + Double.parseDouble(mapDetailVo.get("price").toString());
					List<Map<String, Object>> fifoList;
					if(fifoMap.get(fifoKey) != null){
						fifoList = fifoMap.get(fifoKey);
					}else{
						fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(mapDetailVo));
					}
					Double amount = Double.valueOf(String.valueOf((mapDetailVo.get("amount") == null ? 0 : mapDetailVo.get("amount"))));
					Double imme_amount = null;

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
						detailMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
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
							detailList.add(detailMap);			
							amount = NumberUtil.sub(amount, imme_amount);
						}
					}
					fifoMap.put(fifoKey, keyList);
					if(amount > 0){
						invEnoughMsg.append(String.valueOf(mapDetailVo.get("inv_code"))).append(" ").append(String.valueOf(mapDetailVo.get("inv_name"))).append(",");
					}
				}				
			}
			
			if(detailList.size() == 0){
				return "{\"error\":\"所选单据中的材料库存物资不足!\"}";
			}
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}
			//判断即时库存是否充足
			String invEnough = matCommonMapper.existsMatAffiInvIsEnough(detailList);
			if(invEnough != null && !"".equals(invEnough)){

				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			matAffiOutBackMapper.addMatAffiOutBackMainBatch(mainList);
			matAffiOutBackMapper.addMatAffiOutBackDetail(detailList);
			return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"复制失败 数据库异常 请联系管理员! 方法 copyMatAffiOutCommon\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 冲销  出库单据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String offsetMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		//1、只有出库确认的单据才允许冲账,页面判断
		//2、冲账只针对业务类型：3 科室领用,5 有偿调出,7 无偿调出,9 盘亏出库,11 其他出库,13 报废出库 执行,并且只能单张冲账,页面判断
		//3、
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放mat_out_main字段		
		List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放mat_out_detail字段		
		Integer is_dir = 0;		
		
		String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String[] dates = date.split("-");
		String user_id = SessionManager.getUserId();
		
		try {
			List<Map<String, Object>> listVo = entityList;			
			for(Map<String, Object> tmp : entityList){				
				Map<String, Object> mom = (Map<String, Object>) matAffiOutBackMapper.queryByCode(tmp);				
				Map<String, Object> mainMap = defaultBeanToMap(mom);				
				Long out_id = matAffiOutBackMapper.queryMatAffiOutMainSeq();// 查询序列				
				mainMap.put("out_id",out_id.toString());				
				mainMap.put("state", "1");				
				mainMap.put("is_dir", "0");				
				mainMap.put("out_date", date);
				mainMap.put("year", dates[0]);
				mainMap.put("month", dates[1]);
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);
				if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date"))){
					mainMap.put("out_date", DateUtil.stringToDate(mainMap.get("out_date").toString(), "yyyy-MM-dd"));
				}
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
				mainMap.put("bus_type_code", "30");	
				mainMap.put("brief", "冲销 "+mom.get("out_no")+"出库单");		
				mainMap.put("table_code", "MAT_AFFI_OUT");
				mainMap.put("out_no", matCommonService.getMatNextNo(mainMap));			
				mainList.add(mainMap);
			
				List<Map<String, Object>> list_detail = (List<Map<String, Object>>)matAffiOutBackMapper.queryBackByCodeDetail(tmp);
				for(Map<String, Object> mod:list_detail){					
					Map<String, Object> detailMap = detailBeanToMap(mod);		
					detailMap.put("detail_id", matAffiOutBackMapper.queryMatAffiOutDetailSeq());
					detailMap.put("out_id", mainMap.get("out_id").toString());
					detailMap.put("out_no", mainMap.get("out_no").toString());
					
					if(mod.get("amount") != null && !"".equals(mod.get("amount"))){
						detailMap.put("amount", Double.parseDouble(mod.get("amount").toString())*(-1));	
					}
					if(mod.get("sell_money") != null && !"".equals(mod.get("sell_money"))){
						detailMap.put("sell_money", Double.parseDouble(mod.get("sell_money").toString())*(-1));	
					}
					if(mod.get("allot_money") != null && !"".equals(mod.get("allot_money"))){
						detailMap.put("allot_money", Double.parseDouble(mod.get("allot_money").toString())*(-1));
					}
					if(mod.get("amount_money") != null && !"".equals(mod.get("amount_money"))){
						detailMap.put("amount_money", Double.parseDouble(mod.get("amount_money").toString())*(-1));
					}
					if(mod.get("inva_date") != null && !"".equals(mod.get("inva_date"))){
						detailMap.put("inva_date", DateUtil.stringToDate(mod.get("inva_date").toString(), "yyyy-MM-dd"));
					}
					if(mod.get("disinfect_date") != null && !"".equals(mod.get("disinfect_date"))){
						detailMap.put("disinfect_date", DateUtil.stringToDate(mod.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}
					if(mod.get("sup_id") != null && !"".equals(mod.get("sup_id"))){
						detailMap.put("sup_id", mod.get("sup_id"));	
					}
					if(mod.get("sup_no") != null && !"".equals(mod.get("sup_no"))){
						detailMap.put("sup_no", mod.get("sup_no"));	
					}
					detailList.add(detailMap);
				}
				
			}
			if(detailList.size() > 0){
				matAffiOutBackMapper.addMatAffiOutBackMainBatch(mainList);
				matAffiOutBackMapper.addMatAffiOutBackDetail(detailList);
				return "{\"msg\":\"冲账成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"冲账失败,没有明细数据！\",\"state\":\"false\"}";
			}
					
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"冲账失败 数据库异常 请联系管理员! 方法 offsetMatAffiOutCommon\"}";
		}
		
	}
	
	/**  代销出库  出库确认   
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatAffiOutBackCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try{
			/*
				(1)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！
			    (2)、只有审核状态的单据允许出库确认 (页面判断)
			    (3)、除30代销退库业务之外，其他出库业务 判断材料出库数量是否大于当前库存，若大于，提示：库存不足，出库确认失败！
			    (4)、30代销退库业务：判断材料出库数量是否能容纳这么多退库数量。
			    (5)、按照先进先出算法，将出库单数量写入物流出出库结存表 MAT_FIFO_BALANCE的出库数量中；
			    (6)、同时，将当月出库数量累加到库存结余表：物资批次结余表 MAT_BATCH_BALANCE；物资材料结存表 MAT_BATCH_HOLD；代销材料结余表：MAT_AFFI_BALANCE
			    (7)、修改出库单状态为出库确认（state=3） ，以及确认人，确认日期，出库日期 。 
		    */  
			//出库判断 (1)
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			
			//判断当前期间是否已结账，如结账，则不能添加入库单
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("year", year);
			checkMap.put("month", month);
			checkMap.put("day", day);
			int flag = matCommonMapper.existsMatYearMonthIsAccount(checkMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能确认！\",\"state\":\"false\"}";
			}
			
			List<Map<String, Object>> listUpdateFifoBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvHold = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listMainUpdate = new ArrayList<Map<String, Object>>();//保存修改账表数据
			
			StringBuffer invMsg = new StringBuffer();//存放库存不足的材料信息
			boolean is_enough = true;  //库存是否充足
			
			//获取所选单据中的所有材料
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			detailList = (List<Map<String, Object>>)matAffiOutBackCommonMapper.queryMatAffiOutDetailForConfirm(entityList);
			//查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			
			//存放明细数据用于判断
			Map<String,Map<String,Object>> matAffiFifo = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			
			
			//累计相同材料的数量和金额
			Map<String,Double> sum_amount_map = new HashMap<String,Double>();
			Map<String,Double> sum_amount_money = new HashMap<String,Double>();		
			Map<String,Double> sum_sale_money = new HashMap<String,Double>();				
			//System.out.println("******* detailList:"+detailList.size());
			for(Map<String, Object> mod : detailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mod.get("year").toString();
				//month = mod.get("month").toString();
				//材料编码和名称用于库存不足的提示
				Map<String,Object> mapDetail = new HashMap<String,Object>();
				Map<String, Object> invMap = new HashMap<String, Object>();
				
				mapDetail.put("group_id", mod.get("group_id"));
				mapDetail.put("hos_id", mod.get("hos_id"));
				mapDetail.put("copy_code", mod.get("copy_code"));
				mapDetail.put("store_id", mod.get("store_id"));
				mapDetail.put("inv_id", mod.get("inv_id"));
				mapDetail.put("inv_code", mod.get("inv_code"));
				mapDetail.put("inv_name", mod.get("inv_name"));
				mapDetail.put("batch_no", mod.get("batch_no"));
				mapDetail.put("batch_sn", mod.get("batch_sn"));
				mapDetail.put("bar_code", mod.get("bar_code"));
				mapDetail.put("price", mod.get("price") == null ? 0 : mod.get("price"));
				mapDetail.put("amount", mod.get("amount") == null ? 0 : mod.get("amount"));
				mapDetail.put("amount_money", mod.get("amount_money") == null ? 0 : mod.get("amount_money"));
				mapDetail.put("sale_price", mod.get("sale_price") == null ? 0 : mod.get("sale_price"));
				mapDetail.put("sale_money", mod.get("sale_money") == null ? 0 : mod.get("sale_money"));
				mapDetail.put("location_id", mod.get("location_id") == null ? 0 : mod.get("location_id"));
				
				invMap.put("group_id", mod.get("group_id"));
				invMap.put("hos_id", mod.get("hos_id"));
				invMap.put("copy_code", mod.get("copy_code"));
				invMap.put("year", year);
				invMap.put("month", month);
				invMap.put("store_id", mod.get("store_id"));
				invMap.put("inv_id", mod.get("inv_id"));
				invMap.put("price", mod.get("price"));
				invMap.put("batch_no", mod.get("batch_no"));
				invMap.put("batch_sn", mod.get("batch_sn"));
				invMap.put("bar_code", mod.get("bar_code"));
				invMap.put("location_id", mod.get("location_id") == null ? 0 : mod.get("location_id"));
				
				invList.add(invMap);
				
				//Map中存放<各帐表主键, 具体材料信息的map>用于判断
				matAffiFifo.put(mod.get("store_id").toString()+mod.get("inv_id")+mod.get("price")+mod.get("batch_no")+mod.get("batch_sn")+mod.get("bar_code")+mod.get("location_id"), mapDetail);
				mapInvHold.put(mod.get("store_id").toString()+mod.get("inv_id")+mod.get("location_id"), mapDetail);
				//下面这两张表是区分年月的
				mapDetail.put("year", year);
				mapDetail.put("month", month);
				mapBatchBalance.put(year+month+mod.get("store_id")+mod.get("inv_id")+mod.get("price")+mod.get("batch_no")+mod.get("batch_sn")+mod.get("bar_code")+mod.get("location_id"), mapDetail);
				mapInvBalance.put(year+month+mod.get("store_id")+mod.get("inv_id")+mod.get("location_id"), mapDetail);
				
				//根据材料ID累计数量
				if(sum_amount_map.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")) != null){
					sum_amount_map.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"), NumberUtil.add(sum_amount_map.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")), Double.parseDouble(mod.get("amount").toString())));
				}else{
					sum_amount_map.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"),Double.parseDouble(mod.get("amount").toString()));	
				}
				
				//根据材料ID累计金额
				if(sum_amount_money.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")) != null){
					sum_amount_money.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"), NumberUtil.add(sum_amount_money.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")), Double.parseDouble(mod.get("amount_money").toString())));
				}else{
					sum_amount_money.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"), Double.parseDouble(mod.get("amount_money").toString()));
				}
				//根据材料ID累计金额
				if(sum_sale_money.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")) != null){
					sum_sale_money.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"), NumberUtil.add(sum_sale_money.get(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id")), Double.parseDouble(mod.get("sale_money").toString())));
				}else{
					sum_sale_money.put(mod.get("inv_id").toString()+mod.get("store_id")+mod.get("location_id"), Double.parseDouble(mod.get("sale_money").toString()));
				}
				
				
			}

			//------------------------------查询账表 取出相应数据 并且组织账表数据 MAT_FIFO_BALANCE//mat_affi_fifo ----------------------------------
			List<Map<String,Object>> listFifoBalance = (List<Map<String,Object>>)matAffiFifoMapper.queryByInvList(invList);			
			Map<String,Map<String,Object>> mfb_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MAT_FIFO_BALANCE）的数据都放到map里面			
			for(Map<String,Object> mfb : listFifoBalance){
				String key  = mfb.get("store_id").toString()+mfb.get("inv_id")+mfb.get("price")+mfb.get("batch_no")+mfb.get("batch_sn")+mfb.get("bar_code")+mfb.get("location_id");
				mfb_mb.put(key, mfb);
			}
			
			for (String key : matAffiFifo.keySet()) {//循环当前材料的明细 key查询条件 取相应的值				
				Map<String,Object> mapBatch = new HashMap<String,Object>();
				if(mfb_mb.get(key) !=null){
					Map<String,Object> map = matAffiFifo.get(key);
					mapBatch.putAll(map);
					//选中的单据中材料 map 与 mfb(账表中的材料) 中数量作对比
					Map<String,Object> mfb = mfb_mb.get(key);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					/**判断实际库存是否充足-------------------begin--------------------------*/
					//所选单据材料合计数量 > 0 && 合计数量 > 帐表材料剩余数量 ? 剩余库存不足 : 剩余库存充足（用于出库判断）
					//所选单据材料合计数量 < 0 && (-1)合计数量 > 帐表材料剩余数量 ? 科室材料不足 : 科室材料充足（用于退库判断）
					if((amount > 0 && amount > Double.parseDouble(mfb.get("left_amount").toString())) || (amount < 0 && -1 * amount > Double.parseDouble(mfb.get("out_amount").toString()))){
						invMsg.append(map.get("inv_code")).append(" ").append(map.get("inv_name")).append(",");
						if(is_enough){
							is_enough = false;
						}
					}
					/**判断实际库存是否充足-------------------end----------------------------*/
					if(is_enough){
						//库存充足
						mapBatch.put("out_amount", NumberUtil.add(amount, Double.parseDouble(mfb.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, Double.parseDouble(mfb.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mfb.get("out_sale_money").toString())));//出库批发金额 等于出库批发金额 + 当前出库批发金额
						mapBatch.put("left_amount", NumberUtil.sub(Double.parseDouble(mfb.get("left_amount").toString()), amount));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("left_money", NumberUtil.sub(Double.parseDouble(mfb.get("left_money").toString()), amount_money));//剩余金额 等于剩余金额 - 当前出库金额
						mapBatch.put("left_sale_money", NumberUtil.sub(Double.parseDouble(mfb.get("left_sale_money").toString()), sale_money));//剩余数量 等于剩余数量 - 当前出库数量
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(Double.parseDouble(mfb.get("left_amount").toString()), amount) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(Double.parseDouble(mfb.get("in_money").toString()+mfb.get("store_id")), Double.parseDouble(mapBatch.get("out_money").toString())));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(Double.parseDouble(mfb.get("in_sale_money").toString()+mfb.get("store_id")), Double.parseDouble(mapBatch.get("out_sale_money").toString())));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						listUpdateFifoBalance.add(mapBatch);
					}
				}else{
					//帐表中没有该材料信息，提示库存不足！
					invMsg.append(matAffiFifo.get(key).get("inv_code")).append(" ").append(matAffiFifo.get(key).get("inv_name")).append(",");
					if(is_enough){
						is_enough = false;
					}
				}
			}
			
			//如果材料出现库存物资不足的就不需要组装、判断另外几个帐表了
			if(is_enough){
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_BATCH_BALANCE// mat_affi_batch----------------------------------
				List<Map<String,Object>> listBatchBalance = (List<Map<String,Object>>)matAffiBatchMapper.queryByInvList(invList);

				Map<String,Map<String,Object>> mbb_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MAT_BATCH_BALANCE）的数据都放到map里面
				
				for(Map<String,Object> mbb : listBatchBalance){
					String key = mbb.get("year").toString()+mbb.get("month")+mbb.get("store_id")+mbb.get("inv_id")+mbb.get("price")+mbb.get("batch_no")+mbb.get("batch_sn")+mbb.get("bar_code")+mbb.get("location_id");
					mbb_mb.put(key, mbb);
				}
				
				for (String key : mapBatchBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					
					if(mbb_mb.get(key) !=null){
						Map<String,Object> mbb = mbb_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("out_sale_money").toString())));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_out_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("out_amount").toString())));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("out_money").toString())));//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("out_sale_money").toString())));//剩余批发金额 等于剩余的批发金额 - 当前出库的批发金额
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
				
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_HOLD// mat_affi_inv_hold----------------------------------
				List<Map<String,Object>> listInvHold = (List<Map<String,Object>>)matAffiInvHoldMapper.queryByInvList(invList);
				Map<String,Map<String,Object>> mih_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面			
				for(Map<String,Object> mih : listInvHold){
					String key = mih.get("store_id").toString()+mih.get("inv_id")+mih.get("location_id");
					mih_mb.put(key, mih);
				}
				
				for (String key : mapInvHold.keySet()) {//循环当前材料的明细 key查询条件 取相应的值				
					Map<String,Object> mapBatch = new HashMap<String,Object>();				
					if(mih_mb.get(key) !=null){
						Map<String,Object> map = mapInvHold.get(key);
						mapBatch.putAll(map);				
						Map<String,Object> mih = mih_mb.get(key);	
				
						mapBatch.put("cur_amount", NumberUtil.sub(Double.parseDouble(mih.get("cur_amount").toString()), Double.parseDouble(sum_amount_map.get(mih.get("inv_id").toString()+mih.get("store_id")+mih.get("location_id")).toString())));//出库数量 等于出库的数量 + 当前出库的数量	
						mapBatch.put("cur_money", NumberUtil.sub(Double.parseDouble(mih.get("cur_money").toString()), Double.parseDouble(sum_amount_money.get(mih.get("inv_id").toString()+mih.get("store_id")+mih.get("location_id")).toString())));//出库金额 等于出库的金额 + 当前出库的金额						
					
						listUpdateInvHold.add(mapBatch);
					}
				}

				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_Batch  mat_affi_balance----------------------------------
				List<Map<String,Object>> listInvBalance = (List<Map<String,Object>>)matAffiBalanceMapper.queryByInvList(invList);
				
				Map<String,Map<String,Object>> mib_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面
				for(Map<String,Object> mib : listInvBalance){
					String key = mib.get("year").toString()+mib.get("month")+mib.get("store_id")+mib.get("inv_id")+mib.get("location_id");
					mib_mb.put(key, mib);
				}
				
				for (String key : mapInvBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						Map<String,Object> mib = mib_mb.get(key);
						
						mapBatch.put("out_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_sale_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("y_out_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_amount").toString())));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_money").toString())));//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("inv_id").toString()+mib.get("store_id")+mib.get("location_id")).toString()), Double.parseDouble(mib.get("out_sale_money").toString())));//剩余数量 等于剩余的金额 - 当前出库的金额
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(Double.parseDouble(mib.get("in_amount").toString()), Double.parseDouble(mapBatch.get("out_amount").toString())) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(Double.parseDouble(mib.get("in_money").toString()), Double.parseDouble(mapBatch.get("out_money").toString())));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(Double.parseDouble(mib.get("in_sale_money").toString()), Double.parseDouble(mapBatch.get("out_sale_money").toString())));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
							
						listUpdateInvBalance.add(mapBatch);
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
						mapBatch.put("out_amount", Double.parseDouble(sum_amount_map.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
						mapBatch.put("out_money",  Double.parseDouble(sum_amount_money.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
						mapBatch.put("out_sale_money",  Double.parseDouble(sum_sale_money.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", Double.parseDouble(sum_amount_map.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
						mapBatch.put("y_out_money",  Double.parseDouble(sum_amount_money.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
						mapBatch.put("y_out_sale_money",  Double.parseDouble(sum_sale_money.get(map.get("inv_id").toString()+map.get("store_id")+map.get("location_id")).toString()));
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
			
			for(Map<String,Object> map : entityList){
				Map<String, Object> mainMap = new HashMap<String, Object>();
				mainMap.put("group_id", map.get("group_id"));
				mainMap.put("hos_id", map.get("hos_id"));
				mainMap.put("copy_code", map.get("copy_code"));
				mainMap.put("out_id", map.get("out_id"));
				mainMap.put("state", map.get("state"));
				mainMap.put("year", map.get("year"));
				mainMap.put("month", map.get("month"));
				mainMap.put("confirmer", map.get("confirmer"));
				mainMap.put("confirm_date", map.get("confirm_date"));
						
				listMainUpdate.add(mainMap);
			}
			
			//更新mat_fifo_balance账表
			matAffiFifoMapper.updateBatch(listUpdateFifoBalance);
			
			//添加/更新mat_batch_balance帐表
			if(listAddBatchBalance.size()>0 || listUpdateFifoBalance.size()>0){
				if(listAddBatchBalance.size() >0){
					matAffiBatchMapper.addBatch(listAddBatchBalance);
				}
				if(listUpdateBatchBalance.size() >0){
					matAffiBatchMapper.updateBatch(listUpdateBatchBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_batch_balance帐表无数据变动\"}");
			}
			
			//添加/更新mat_inv_balance帐表
			if(listAddInvBalance.size()>0 || listUpdateInvBalance.size()>0){
				if(listAddInvBalance.size()>0){
					matAffiBalanceMapper.addBatch(listAddInvBalance);
				}
				if(listUpdateInvBalance.size() >0){
					matAffiBalanceMapper.updateBatch(listUpdateInvBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_inv_balance帐表无数据变动\"}");
			}
			
			//更新mat_inv_hold帐表
			matAffiInvHoldMapper.updateBatch(listUpdateInvHold);
			
			//修改状态为出库确认
			matAffiOutBackMapper.updateBatchConfirm(listMainUpdate);
			
			return "{\"msg\":\"出库确认成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}

	}
	
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * @Description 
	 * 根据主键查询主表数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matAffiOutBackMapper.queryByCode(entityMap);
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得上一张、下一张ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAffiOutBackIds(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object>  mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		
		String out_idUp = matAffiOutBackCommonMapper.queryAffiUpOutBackId(entityMap);//上一张ID
		if(out_idUp == null){
			mapVo.put("out_idUp", entityMap.get("out_id"));
		}else{
			mapVo.put("out_idUp", Integer.parseInt(out_idUp.toString()));
		}
		
		String out_idNext = matAffiOutBackCommonMapper.queryAffiNextOutBackId(entityMap); //下一张ID
		if(out_idNext == null){
			mapVo.put("out_idNext", entityMap.get("out_id"));
		}else{
			mapVo.put("out_idNext", Integer.parseInt(out_idNext.toString()));
		}
		
		return mapVo;
		
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
	public String queryAffiOutBackDetailById(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackMapper.queryAffiOutBackDetailById(entityMap);
			return ChdJson.toJsonLower(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackMapper.queryAffiOutBackDetailById(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

		
	@Override
	public String queryAffiOutBackDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiOutBackMapper.queryAffiOutBackDetailByCode(entityMap);
		
		return matCommonService.getAffiDetailJsonByDetailList(JsonListMapUtil.toListMapLower(list));
	}
		
	public Map<String, Object> defaultBeanToMap(Map<String, Object> mom) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", mom.get("group_id"));
		map.put("hos_id",  mom.get("hos_id"));
		map.put("copy_code", mom.get("copy_code"));
		map.put("out_id", mom.get("out_id"));
		map.put("year",mom.get("year"));
		map.put("month",mom.get("month"));
		map.put("bus_type_code", mom.get("bus_type_code"));
		map.put("store_id", mom.get("store_id"));
		map.put("store_no",mom.get("store_no"));
		map.put("out_no", mom.get("out_no"));
		map.put("brief", mom.get("brief"));
		map.put("out_date", mom.get("out_date"));
		map.put("dept_id", mom.get("dept_id"));
		map.put("dept_no", mom.get("dept_no"));
		map.put("dept_emp", mom.get("dept_emp"));
		map.put("use_code", mom.get("use_code"));
		map.put("proj_id", mom.get("proj_id"));
		map.put("maker", mom.get("maker"));
		map.put("checker", mom.get("checker"));
		map.put("check_date", mom.get("check_date"));
		map.put("confirmer", mom.get("confirmer"));
		map.put("confirm_date", mom.get("confirm_date"));
		map.put("state",mom.get("state"));
		map.put("is_dir", mom.get("is_dir"));
		map.put("his_flag", mom.get("his_flag"));

		return map;
	}

	public Map<String, Object> detailBeanToMap(Map<String, Object> mod) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", mod.get("group_id"));
		map.put("hos_id", mod.get("hos_id"));
		map.put("copy_code", mod.get("copy_code"));
		map.put("store_id", mod.get("store_id"));
		map.put("out_id", mod.get("out_id"));
		// map.put("out_detail_id", 0);
		map.put("inv_id", mod.get("inv_id"));
		map.put("inv_no", mod.get("inv_no"));
		map.put("batch_sn", mod.get("batch_sn"));
		map.put("batch_no", mod.get("batch_no"));
		map.put("price", mod.get("price"));
		map.put("amount", mod.get("amount"));
		map.put("sale_price", mod.get("sale_price"));
		map.put("sale_money", mod.get("sale_money") != null ? mod.get("sale_money") : "0");
		map.put("sell_price", mod.get("sell_price"));
		map.put("sell_money", mod.get("sell_money") != null ? mod.get("sell_money") : "0");
		map.put("allot_price", mod.get("allot_price") != null ? mod.get("allot_price") : "0");
		map.put("allot_money", mod.get("allot_money") != null ? mod.get("allot_money") : "0");
		map.put("amount_money", mod.get("amount_money") != null ? mod.get("amount_money") : "0");
		map.put("num_exchange", mod.get("num_exchange") != null ? mod.get("num_exchange") : "0");
		map.put("pack_price", mod.get("pack_price") != null ? mod.get("pack_price") : "0");
		map.put("num", mod.get("num") != null ? mod.get("num") : "0");
		map.put("bar_code", mod.get("bar_code") != null ? mod.get("bar_code") : "");
		map.put("inva_date", mod.get("inva_date") != null ? mod.get("inva_date"): "");
		map.put("disinfect_date", mod.get("disinfect_date") != null ? mod.get("disinfect_date"): "");
		map.put("location_id", mod.get("location_id") != null ? mod.get("location_id") : "0");
		map.put("note", mod.get("note") != null ? mod.get("note") : "");
		map.put("pack_code", mod.get("pack_code") != null ? mod.get("pack_code") : "");

		return map;
	}	
	
	
	//出库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String matAffiOutBackCommonService(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				//查询入库主表
				if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
					List<Map<String, Object>> map =matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					//查询出库主表
					Map<String,Object> map=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByMain(entityMap);
					
					//查询出库明细表
					List<Map<String,Object>> list=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
					
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			
		}
		
		/**
		 * @Description 查询材料批次列表
		 * @param mapVo
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@Override
		public String queryMatAffiInvBatchList(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {
				
				List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matAffiOutBackCommonMapper.queryMatAffiInvBatchList(entityMap));
				
				return ChdJson.toJson(list);
			} else {
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matAffiOutBackCommonMapper.queryMatAffiInvBatchList(entityMap, rowBounds));
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		
		/**
		 * @Description 选择材料返回List
		 * @param entityMap
		 * @return String
		 * @throws Exception
		 */
		@Override
		public String queryAffiOutInvListByChoiceInv(List<Map<String,Object>> entityList) throws DataAccessException{
			try {
				return matCommonService.getAffiInvJsonByAffiInvList(entityList);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");
			}
		}
		
		//显示明细
		@Override
		public String queryDetails(Map<String,Object> entityMap) throws DataAccessException{
			
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			entityMap.put("user_id", SessionManager.getUserId());
			
			if (sysPage.getTotal()==-1){
				List<Map<String,Object>> list = matAffiOutBackCommonMapper.queryDetails(entityMap);			
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = matAffiOutBackCommonMapper.queryDetails(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);			
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
		@Override
		public Map<String, Object> matAffiOutCommonTemplate(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatAffiOutBackCommonMapper matAffiOutBackCommonMapper = (MatAffiOutBackCommonMapper)context.getBean("matAffiOutBackCommonMapper");
				
				
				//查询入库主表
				if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
					List<Map<String, Object>> map =matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByDetail(entityMap);
					reMap.put("main", map);
					reMap.put("detail", list);
				}else{
					//查询出库主表
					Map<String,Object> map=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByMain(entityMap);
					
					//查询出库明细表
					List<Map<String,Object>> list=matAffiOutBackCommonMapper.queryMatAffiOutBackPrintTemlateByDetail(entityMap);
					reMap.put("main", map);
					reMap.put("detail", list);
					
				}
				
				return reMap;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			
		}
		
		@Override
		public void updateMatAffiOutRela(Map<String, Object> entityMap) throws DataAccessException {
			List<Map<String,Object>> relaList= JsonListMapUtil.toListMapLower(matAffiOutBackCommonMapper.queryMatAffiOutRela(entityMap));
			if(relaList.size()>0){
				matAffiOutBackCommonMapper.deleteMatAffiOutRela(relaList);
			}
			
		}
		/**
		 * 查看出库单是否是科室申领生成的
		 */
		@Override
		public int queryMatAffiOutBackIsApply(Map<String, Object> entityMap) throws DataAccessException {
			Integer  is_apply = matAffiOutBackCommonMapper.queryMatOutBackMainIsApply(entityMap);
			return is_apply;
		}
}
