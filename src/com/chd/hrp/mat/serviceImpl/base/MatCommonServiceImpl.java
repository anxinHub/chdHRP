package com.chd.hrp.mat.serviceImpl.base;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.github.pagehelper.PageInfo;

@Service("matCommonService")
public class MatCommonServiceImpl implements MatCommonService {  
	
	private static Logger logger = Logger.getLogger(MatCommonServiceImpl.class); 
	  
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;  
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	//引入DAO操作
	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;
	
	@Override
	public String getMatHosRules(Map<String, Object> entityMap) throws DataAccessException {
		
		return matCommonMapper.getMatHosRules(entityMap);
	}
	
	@Override
	public String getMatAccPara(Map<String, Object> entityMap) throws DataAccessException {
		
		return matCommonMapper.getMatAccPara(entityMap);
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
		String flag = matCommonMapper.getMatAccPara(entityMap); //0: 纯数字，1: 数字和字母组合
		
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
	public String queryMatInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	//	//材料字典列表代销入库
	@Override
	public String queryMatAffiInvListIn(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatAffiInvListIn(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatAffiInvListIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	//材料字典列表(有材料库存)  科室申领
		@Override
		public String queryMatInvListApply(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = matCommonMapper.queryMatInvListApply(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = matCommonMapper.queryMatInvListApply(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		
	
	
	//材料字典列表(专购品入库)
		@Override
		public String queryMatInvListSpecial(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = matCommonMapper.queryMatInvListSpecial(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = matCommonMapper.queryMatInvListSpecial(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
	
	//材料字典列表(没有材料库存,不带仓库)
	@Override
	public String queryMatInvListNotStore(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
			
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatInvListNotStore(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatInvListNotStore(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//代销材料字典列表
	@Override
	public String queryMatAffiInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatAffiInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatAffiInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 普通库存材料列表（不含批次）  库存盘点
	@Override
	public String queryMatStockInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockSumInvList(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockSumInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	// 普通库存材料列表（不含批次）  材料调拨 
		@Override
		public String queryMatStockInvListTran(Map<String, Object> entityMap) throws DataAccessException {
 
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			//---开始---东阳需求,已经显示的材料,材料选择列表不再显示
			if (entityMap.get("invMsg")!=null && StringUtils.isNotBlank(entityMap.get("invMsg").toString())) {
				String[] invMsgs = entityMap.get("invMsg").toString().split("@");
				entityMap.put("invMsg", invMsgs);
			}
			//---结束---东阳需求,已经显示的材料,材料选择列表不再显示
			
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListTran(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListTran(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
	
	// 普通库存材料列表（不含批次） 材料出库 
		@Override
		public String queryMatStockInvListOut(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			//---开始---东阳需求,已经显示的材料,材料选择列表不再显示
//			List<Map<String, Object>> invMsgList=new ArrayList<Map<String,Object>>();
//			Map<String,Object> invMsgMap=new HashMap<String, Object>();
			if (entityMap.get("invMsg")!=null && StringUtils.isNotBlank(entityMap.get("invMsg").toString())) {
				String[] invMsgs = entityMap.get("invMsg").toString().split("@");
//				for (int i = 0; i < invMsgs.length; i++) {
//					invMsgMap=new HashMap<String, Object>();
//					invMsgMap.put("inv", invMsgs[i].split(",")[0]+","+invMsgs[i].split(",")[1]+","+invMsgs[i].split(",")[2]);
//					invMsgMap.put("price", invMsgs[i].split(",")[3]);
//					invMsgList.add(invMsgMap);
//				}
				entityMap.put("invMsg", invMsgs);
			}
			//---结束---东阳需求,已经显示的材料,材料选择列表不再显示
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListOut(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListOut(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
		// 普通库存材料列表（不含批次） 科室退库
				@Override
				public String queryMatStockInvListOutByBack(Map<String, Object> entityMap) throws DataAccessException {

					SysPage sysPage = new SysPage();
					sysPage = (SysPage) entityMap.get("sysPage");
					
					
					//---开始---东阳需求,已经显示的材料,材料选择列表不再显示
					if (entityMap.get("invMsg")!=null && StringUtils.isNotBlank(entityMap.get("invMsg").toString())) {
						String[] invMsgs = entityMap.get("invMsg").toString().split("@");
						entityMap.put("invMsg", invMsgs);
					}
					//---结束---东阳需求,已经显示的材料,材料选择列表不再显示
					
					if (sysPage.getTotal()==-1){
						List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListOutByBack(entityMap);
						return ChdJson.toJsonLower(list);
					}else{
						RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
						List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListOutByBack(entityMap, rowBounds);
						PageInfo page = new PageInfo(list);
						
						return ChdJson.toJsonLower(list, page.getTotal());
					}
				}
				
		
		
	// 普通库存材料列表（不含批次）材料退货 
		@Override
		public String queryMatStockInvListBack(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListBack(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListBack(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		}
		
		
	// 普通库存材料明细列表（含批次）
	@Override
	public String queryMatStockInvDetailList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(entityMap.containsKey("amount")){
			if(Double.parseDouble(entityMap.get("amount").toString()) > 0){
				entityMap.put("flag", "1");
			}else{
				entityMap.put("flag", "0");
			}
		}else{
			entityMap.put("flag", "1");
		}
		
		if(entityMap.get("inva_date")!=null && !"".equals(entityMap.get("inva_date"))){
			entityMap.put("inva_date", DateUtil.stringToDate( entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("disinfect_date")!=null && !"".equals(entityMap.get("disinfect_date"))){
			entityMap.put("disinfect_date", DateUtil.stringToDate( entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
	}
	
	/**
	 * 用于出库、调拨、退货明细数据加载（传入明细数据自动根据批次信息合并材料，且把批次信息组装成json格式）
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override   
	public String getDetailJsonByDetailList(List<Map<String, Object>> entityList) throws DataAccessException{
		
		//组装明细结果集
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		
		//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
		Map<String, Map<String, Object>> invMap = new LinkedHashMap<String, Map<String, Object>>();
		
		//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
		Map<String, Map<String, Object>> invJsonMap = new LinkedHashMap<String, Map<String, Object>>();
		
		try{
			String invKey = "";  //材料Map的key值
			String invJsonKey = "";  //材料JsonMap的key值
			String stockMsg = "";  //材料库存信息
			String stockSnMsg = "";  //材料批次库存信息
			Double amount = null;  //批次数量
			Double amount_money = null;  //金额
			Double sell_money = null;  //零售金额
			Double sale_money = null;  //批发金额
			Double allot_money = null;  //调拨金额
			Double cur_amount = null;  //批发金额
			Double imme_amount = null;  //调拨金额
			int state = 0;  //单据状态
			String detail_id_name = "";  //主键名称(in_detail_id, out_detail_id, tran_detail_id)
			String detail_id_value = "";  //主键值
			String main_id_name = "";  //主键名称(in_id, out_id, tran_id)
			String main_id_value = "";  //主键值
			boolean is_check = false;
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				//获取主键
				if(!is_check){
					if(entityMap.containsKey("in_id")){
						detail_id_name = "in_detail_id";
						main_id_name = "in_id";
					}else if(entityMap.containsKey("out_id")){
						detail_id_name = "out_detail_id";
						main_id_name = "out_id";
					}else if(entityMap.containsKey("tran_id")){
						detail_id_name = "tran_detail_id";
						main_id_name = "tran_id";
					}else{
						throw new SysException("查询结果必须含主从表主键，请修改！");
					}
					is_check = true;
				}
				detail_id_value = entityMap.get(detail_id_name).toString();
				main_id_value = entityMap.get(main_id_name).toString();
				//获取invKey
				invKey = entityMap.get("inv_id").toString() + entityMap.get("price").toString() + 
						entityMap.get("batch_no").toString() + entityMap.get("bar_code").toString();
				
				if(entityMap.containsKey("inva_date") && entityMap.get("inva_date") !=null){
					invKey = invKey+entityMap.get("inva_date").toString();
				}
				if(entityMap.containsKey("disinfect_date") && entityMap.get("disinfect_date") !=null){
					invKey = invKey+entityMap.get("disinfect_date").toString();
				}
				
				//获取invJsonKey
				invJsonKey = invKey + entityMap.get("batch_sn").toString();
				//获取单据状态(如果不传该值为了防止报错默认为已确认)
				state = entityMap.get("state") == null ? 3 : Integer.valueOf(entityMap.get("state").toString());
				//获取当前数量、金额
				amount = Double.valueOf(entityMap.get("amount").toString());
				amount_money = Double.valueOf(entityMap.get("amount_money").toString());
				sale_money = entityMap.get("sale_money") == null ? 0 : Double.valueOf(entityMap.get("sale_money").toString());
				sell_money = entityMap.get("sell_money") == null ? 0 : Double.valueOf(entityMap.get("sell_money").toString());
				allot_money = entityMap.get("allot_money") == null ? 0 : Double.valueOf(entityMap.get("allot_money").toString());
				//获取库存和及时库存
				cur_amount = entityMap.get("cur_amount") == null ? 0 : Double.valueOf(entityMap.get("cur_amount").toString());
				imme_amount = entityMap.get("imme_amount") == null ? 0 : Double.valueOf(entityMap.get("imme_amount").toString());
				
				//用于按批号条码查询库存/即时库存
				/*Map<String,Object> stockMap =new HashMap<String,Object>();
				stockMap.put("group_id", entityMap.get("group_id"));
				stockMap.put("hos_id", entityMap.get("hos_id"));
				stockMap.put("copy_code", entityMap.get("copy_code"));
				stockMap.put("store_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_store_id") : entityMap.get("store_id"));
				stockMap.put("inv_id", entityMap.get("inv_id"));
				stockMap.put("price", entityMap.get("price"));
				stockMap.put("batch_no", entityMap.get("batch_no"));
				stockMap.put("bar_code", entityMap.get("bar_code"));
				stockMap.put("location_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_location_id") : entityMap.get("location_id"));
				if(entityMap.containsKey("inva_date") && entityMap.get("inva_date") !=null){
					stockMap.put("inva_date", DateUtil.stringToDate(entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("disinfect_date") && entityMap.get("disinfect_date") !=null){
					stockMap.put("disinfect_date", DateUtil.stringToDate(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("bus_type_code")  && entityMap.get("bus_type_code") !=null){
					stockMap.put("in_id", entityMap.get("in_id"));
				}*/
				
				//用于按批号批次条码查询库存/即时库存
				/*Map<String,Object> stockSnMap =new HashMap<String,Object>();
				stockSnMap.put("group_id", entityMap.get("group_id"));
				stockSnMap.put("hos_id", entityMap.get("hos_id"));
				stockSnMap.put("copy_code", entityMap.get("copy_code"));
				stockSnMap.put("store_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_store_id") : entityMap.get("store_id"));
				stockSnMap.put("inv_id", entityMap.get("inv_id"));
				stockSnMap.put("price", entityMap.get("price"));
				stockSnMap.put("batch_no", entityMap.get("batch_no")); 
				stockSnMap.put("batch_sn", entityMap.get("batch_sn"));
				stockSnMap.put("bar_code", entityMap.get("bar_code"));
				stockSnMap.put("location_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_location_id") : entityMap.get("location_id"));
				
				if(entityMap.containsKey("inva_date") && entityMap.get("inva_date") !=null){
					stockSnMap.put("inva_date", DateUtil.stringToDate(entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("disinfect_date") && entityMap.get("disinfect_date") !=null){
					stockSnMap.put("disinfect_date", DateUtil.stringToDate(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
				}
				
				if(entityMap.containsKey("bus_type_code") && entityMap.get("bus_type_code") !=null){
					stockSnMap.put("in_id", entityMap.get("in_id"));
				}*/
				
				//用于存储信息
				Map<String, Object> detailMap = new HashMap<String, Object>();
				Map<String, Object> detailJsonMap = new HashMap<String, Object>();
				//判断材料是否存在
				if(invMap.containsKey(invKey)){
					/*********组装明细主信息********begin********/
					//获取已有的detailMap
					detailMap = invMap.get(invKey);
					//累加数量、金额
					detailMap.put("sum_amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));  //不可修改，用于页面中的明细批次显示方式
					detailMap.put("amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));
					detailMap.put("amount_money", NumberUtil.add(Double.valueOf(detailMap.get("amount_money").toString()), amount_money));
					detailMap.put("sale_money", NumberUtil.add(Double.valueOf(detailMap.get("sale_money").toString()), sale_money));
					detailMap.put("sell_money", NumberUtil.add(Double.valueOf(detailMap.get("sell_money").toString()), sell_money));
					detailMap.put("allot_money", NumberUtil.add(Double.valueOf(detailMap.get("allot_money").toString()), allot_money));
					//库存叠加
					detailMap.put("cur_amount", NumberUtil.add(Double.valueOf(detailMap.get("cur_amount").toString()), cur_amount));
					detailMap.put("imme_amount", NumberUtil.add(Double.valueOf(detailMap.get("imme_amount").toString()), imme_amount));
					
					//库存和及时库存从明细里面去
					
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					/*if(state != 3 && !("out_id".equals(main_id_name) && amount < 0)){
						detailMap.put("imme_amount", NumberUtil.add(Double.valueOf(detailMap.get("imme_amount").toString()), amount < 0 ? -1 * amount : amount));
					}*/
					invMap.put(invKey, detailMap);
					/*********组装明细主信息********end**********/
					/*********组装明细批次信息******begin********/
					detailJsonMap.put(main_id_name, main_id_value);
					detailJsonMap.put(detail_id_name, detail_id_value);
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("batch_sn", entityMap.get("batch_sn"));
					detailJsonMap.put("amount", entityMap.get("amount"));
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("sale_price", entityMap.get("sale_price"));
					detailJsonMap.put("sell_price", entityMap.get("sell_price"));
					detailJsonMap.put("allot_price", entityMap.get("allot_price"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("sale_money", sale_money);
					detailJsonMap.put("sell_money", sell_money);
					detailJsonMap.put("allot_money", allot_money);
					detailJsonMap.put("cur_amount", cur_amount);
					detailJsonMap.put("imme_amount", imme_amount);
					//获取批次库存信息
					//库存明细带出来不在进数据库查表
					/*stockSnMsg = matCommonMapper.getInvStockByInvMsg(stockSnMap);
					detailJsonMap.put("cur_amount", stockSnMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailJsonMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ? Double.valueOf(stockSnMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockSnMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					*/
					invJsonMap.put(invJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
				}else{
					/*********组装明细批次信息******begin********/
					detailJsonMap.put(main_id_name, main_id_value);
					detailJsonMap.put(detail_id_name, detail_id_value);
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("batch_sn", entityMap.get("batch_sn"));
					detailJsonMap.put("amount", entityMap.get("amount"));
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("sale_price", entityMap.get("sale_price"));
					detailJsonMap.put("sell_price", entityMap.get("sell_price"));
					detailJsonMap.put("allot_price", entityMap.get("allot_price"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("sale_money", sale_money);
					detailJsonMap.put("sell_money", sell_money);
					detailJsonMap.put("allot_money", allot_money);
					
					detailJsonMap.put("cur_amount", cur_amount);
					detailJsonMap.put("imme_amount", imme_amount);
					/*
					//获取批次库存信息
					stockSnMsg = matCommonMapper.getInvStockByInvMsg(stockSnMap);
					detailJsonMap.put("cur_amount", stockSnMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailJsonMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ? Double.valueOf(stockSnMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockSnMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					*/
					invJsonMap.put(invJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
					/*********组装明细主信息********begin********/
					detailMap = new HashMap<String,Object>();
					entityMap.remove("batch_sn");
					entityMap.remove(detail_id_name);
					detailMap.putAll(entityMap);
					//重新对金额复制，防止出现null值导致报错
					detailMap.put("amount_money", amount_money);
					detailMap.put("sale_money", sale_money);
					detailMap.put("sell_money", sell_money);
					detailMap.put("allot_money", allot_money);
					detailMap.put("sum_amount", amount);  //不可修改，用于页面中的明细批次显示方式
					//库存 及时库存
					detailMap.put("cur_amount", cur_amount);
					detailMap.put("imme_amount", imme_amount);
					/*
					//获取主库存和及时库存
					stockMsg = matCommonMapper.getInvStockByInvMsg(stockMap);
					detailMap.put("cur_amount", stockMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ?  Double.valueOf(stockMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					*/
					invMap.put(invKey, detailMap);
					/*********组装明细主信息********end**********/
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				} 
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
		return ChdJson.toJson(detailList);
	}
	
	/**
	 * 用于代销出库、调拨、退货明细数据加载（传入明细数据自动根据批次信息合并材料，且把批次信息组装成json格式）
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override   
	public String getAffiDetailJsonByDetailList(List<Map<String, Object>> entityList) throws DataAccessException{
		
		//组装明细结果集
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		
		//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
		Map<String, Map<String, Object>> invMap = new LinkedHashMap<String, Map<String, Object>>();
		
		//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
		Map<String, Map<String, Object>> invJsonMap = new LinkedHashMap<String, Map<String, Object>>();
		
		try{
			String invKey = "";  //材料Map的key值
			String invJsonKey = "";  //材料JsonMap的key值
			String stockMsg = "";  //材料库存信息
			String stockSnMsg = "";  //材料批次库存信息
			Double amount = null;  //批次数量
			Double amount_money = null;  //金额
			Double sell_money = null;  //零售金额
			Double sale_money = null;  //批发金额
			Double allot_money = null;  //调拨金额
			int state = 0;  //单据状态
			String detail_id_name = "";  //主键名称(in_detail_id, out_detail_id, tran_detail_id)
			String detail_id_value = "";  //主键值
			String main_id_name = "";  //主键名称(in_id, out_id, tran_id)
			String main_id_value = "";  //主键值
			boolean is_check = false;
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				//获取主键
				if(!is_check){
					if(entityMap.containsKey("in_id")){
						detail_id_name = "detail_id";
						main_id_name = "in_id";
					}else if(entityMap.containsKey("out_id")){
						detail_id_name = "detail_id";
						main_id_name = "out_id";
					}else if(entityMap.containsKey("tran_id")){
						detail_id_name = "tran_detail_id";
						main_id_name = "tran_id";
					}else{
						throw new SysException("查询结果必须含主从表主键，请修改！");
					}
					is_check = true;
				}
				detail_id_value = entityMap.get(detail_id_name).toString();
				main_id_value = entityMap.get(main_id_name).toString();
				//获取invKey
				invKey = entityMap.get("inv_id").toString() + entityMap.get("price").toString() + 
						entityMap.get("batch_no").toString() + entityMap.get("bar_code").toString();
				//获取invJsonKey
				invJsonKey = invKey + entityMap.get("batch_sn").toString();
				//获取单据状态(如果不传该值为了防止报错默认为已确认)
				state = entityMap.get("state") == null ? 3 : Integer.valueOf(entityMap.get("state").toString());
				//获取当前数量、金额
				amount = Double.valueOf(entityMap.get("amount").toString());
				amount_money = Double.valueOf(entityMap.get("amount_money").toString());
				sale_money = entityMap.get("sale_money") == null ? 0 : Double.valueOf(entityMap.get("sale_money").toString());
				sell_money = entityMap.get("sell_money") == null ? 0 : Double.valueOf(entityMap.get("sell_money").toString());
				allot_money = entityMap.get("allot_money") == null ? 0 : Double.valueOf(entityMap.get("allot_money").toString());
				//用于按批号条码查询库存/即时库存
				Map<String,Object> stockMap =new HashMap<String,Object>();
				stockMap.put("group_id", entityMap.get("group_id"));
				stockMap.put("hos_id", entityMap.get("hos_id"));
				stockMap.put("copy_code", entityMap.get("copy_code"));
				stockMap.put("store_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_store_id") : entityMap.get("store_id"));
				stockMap.put("inv_id", entityMap.get("inv_id"));
				stockMap.put("price", entityMap.get("price"));
				stockMap.put("batch_no", entityMap.get("batch_no"));
				stockMap.put("bar_code", entityMap.get("bar_code"));
				stockMap.put("location_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_location_id") : entityMap.get("location_id"));
				if(entityMap.containsKey("inva_date") && entityMap.get("inva_date") !=null){
					stockMap.put("inva_date", DateUtil.stringToDate(entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("disinfect_date") && entityMap.get("disinfect_date") !=null){
					stockMap.put("disinfect_date", DateUtil.stringToDate(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("bus_type_code") && entityMap.get("bus_type_code") !=null){
					stockMap.put("in_id", entityMap.get("in_id"));
				}
				
				//用于按批号批次条码查询库存/即时库存
				Map<String,Object> stockSnMap =new HashMap<String,Object>();
				stockSnMap.put("group_id", entityMap.get("group_id"));
				stockSnMap.put("hos_id", entityMap.get("hos_id"));
				stockSnMap.put("copy_code", entityMap.get("copy_code"));
				stockSnMap.put("store_id", detail_id_name.equals("tran_detail_id") ? entityMap.get("out_store_id") : entityMap.get("store_id"));
				stockSnMap.put("inv_id", entityMap.get("inv_id"));
				stockSnMap.put("price", entityMap.get("price"));
				stockSnMap.put("batch_no", entityMap.get("batch_no"));
				stockSnMap.put("batch_sn", entityMap.get("batch_sn"));
				stockSnMap.put("bar_code", entityMap.get("bar_code"));
				if(entityMap.containsKey("inva_date") && entityMap.get("inva_date") !=null){
					stockSnMap.put("inva_date", DateUtil.stringToDate(entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("disinfect_date") && entityMap.get("disinfect_date") !=null){
					stockSnMap.put("disinfect_date", DateUtil.stringToDate(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
				}
				if(entityMap.containsKey("bus_type_code") && entityMap.get("bus_type_code") !=null){
					stockSnMap.put("in_id", entityMap.get("in_id"));
				}
				
				//用于存储信息
				Map<String, Object> detailMap = new HashMap<String, Object>();
				Map<String, Object> detailJsonMap = new HashMap<String, Object>();
				//判断材料是否存在
				if(invMap.containsKey(invKey)){
					/*********组装明细主信息********begin********/
					//获取已有的detailMap
					detailMap = invMap.get(invKey);
					//累加数量、金额
					detailMap.put("sum_amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));  //不可修改，用于页面中的明细批次显示方式
					detailMap.put("amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));
					detailMap.put("amount_money", NumberUtil.add(Double.valueOf(detailMap.get("amount_money").toString()), amount_money));
					detailMap.put("sale_money", NumberUtil.add(Double.valueOf(detailMap.get("sale_money").toString()), sale_money));
					detailMap.put("sell_money", NumberUtil.add(Double.valueOf(detailMap.get("sell_money").toString()), sell_money));
					detailMap.put("allot_money", NumberUtil.add(Double.valueOf(detailMap.get("allot_money").toString()), allot_money));
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					if(state != 3 && !("out_id".equals(main_id_name) && amount < 0)){
						detailMap.put("imme_amount", NumberUtil.add(Double.valueOf(detailMap.get("imme_amount").toString()), amount < 0 ? -1 * amount : amount));
					}
					invMap.put(invKey, detailMap);
					/*********组装明细主信息********end**********/
					/*********组装明细批次信息******begin********/
					detailJsonMap.put(main_id_name, main_id_value);
					detailJsonMap.put(detail_id_name, detail_id_value);
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("batch_sn", entityMap.get("batch_sn"));
					detailJsonMap.put("amount", entityMap.get("amount"));
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("sale_price", entityMap.get("sale_price"));
					detailJsonMap.put("sell_price", entityMap.get("sell_price"));
					detailJsonMap.put("allot_price", entityMap.get("allot_price"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("sale_money", sale_money);
					detailJsonMap.put("sell_money", sell_money);
					detailJsonMap.put("allot_money", allot_money);
					//获取批次库存信息
					stockSnMsg = matCommonMapper.getAffiInvStockByInvMsg(stockSnMap);
					detailJsonMap.put("cur_amount", stockSnMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailJsonMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ? Double.valueOf(stockSnMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockSnMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					invJsonMap.put(invJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
				}else{
					/*********组装明细批次信息******begin********/
					detailJsonMap.put(main_id_name, main_id_value);
					detailJsonMap.put(detail_id_name, detail_id_value);
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("batch_sn", entityMap.get("batch_sn"));
					detailJsonMap.put("amount", entityMap.get("amount"));
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("sale_price", entityMap.get("sale_price"));
					detailJsonMap.put("sell_price", entityMap.get("sell_price"));
					detailJsonMap.put("allot_price", entityMap.get("allot_price"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("sale_money", sale_money);
					detailJsonMap.put("sell_money", sell_money);
					detailJsonMap.put("allot_money", allot_money);
					//获取批次库存信息
					stockSnMsg = matCommonMapper.getAffiInvStockByInvMsg(stockSnMap);
					detailJsonMap.put("cur_amount", stockSnMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailJsonMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ? Double.valueOf(stockSnMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockSnMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					invJsonMap.put(invJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
					/*********组装明细主信息********begin********/
					detailMap = new HashMap<String,Object>();
					entityMap.remove("batch_sn");
					entityMap.remove(detail_id_name);
					detailMap.putAll(entityMap);
					//重新对金额复制，防止出现null值导致报错
					detailMap.put("amount_money", amount_money);
					detailMap.put("sale_money", sale_money);
					detailMap.put("sell_money", sell_money);
					detailMap.put("allot_money", allot_money);
					detailMap.put("sum_amount", amount);  //不可修改，用于页面中的明细批次显示方式
					//获取主库存和及时库存
					stockMsg = matCommonMapper.getAffiInvStockByInvMsg(stockMap);
					detailMap.put("cur_amount", stockMsg.split("@")[0]);
					//未确认及时库存显示时需加上本单据占用数量(注：退库未确认的不计入及时库存)
					detailMap.put("imme_amount", (state == 3 || ("out_id".equals(main_id_name) && amount < 0)) ?  Double.valueOf(stockMsg.split("@")[1]) : NumberUtil.add(Double.valueOf(stockMsg.split("@")[1]), amount < 0 ? -1 * amount : amount));
					invMap.put(invKey, detailMap);
					/*********组装明细主信息********end**********/
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				} 
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
		return ChdJson.toJson(detailList);
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
			Map<String, Map<String, Object>> invMap = new LinkedHashMap<String, Map<String, Object>>();
			
			//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
			Map<String, Map<String, Object>> invJsonMap = new LinkedHashMap<String, Map<String, Object>>();
			
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

			/***获取相关材料库存批号批次条码信息**********begin***************/
			Map<String, Object> queryMap = new HashMap<String, Object>();
			boolean is_frist = true;
			StringBuffer inv_ids = new StringBuffer();
			for(Map<String, Object> entityMap : entityList){
				if(is_frist){
					queryMap.put("group_id", entityMap.get("group_id"));
					queryMap.put("hos_id", entityMap.get("hos_id"));
					queryMap.put("copy_code", entityMap.get("copy_code"));
					queryMap.put("store_id", entityMap.get("store_id"));

					if(entityMap.containsKey("amount")){
						if(Double.parseDouble(entityMap.get("amount").toString()) > 0){
							queryMap.put("flag", "1");
						}else{
							queryMap.put("flag", "0");
						}
					}else{
						queryMap.put("flag", "1");
					}
					inv_ids.append(entityMap.get("inv_id").toString());
					is_frist = false;
				}else{
					inv_ids.append(",").append(entityMap.get("inv_id").toString());
				}
			}
			queryMap.put("inv_ids", inv_ids.toString());
			List<Map<String, Object>> queryFifoMap = toListMapLower(matCommonMapper.queryMatFifoByInvList(queryMap));
			for(Map<String, Object> fifoMap : queryFifoMap){
				immeKey = fifoMap.get("inv_id").toString();
				List<Map<String, Object>> list;
				if(immeMap.containsKey(immeKey)){
					list = immeMap.get(immeKey);
				}else{
					list = new ArrayList<Map<String,Object>>();
				}
				list.add(fifoMap);
				
				immeMap.put(immeKey, list);
			}
			/***获取相关材料库存批号批次条码信息**********end*****************/
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				amount = Double.valueOf(entityMap.get("amount") == null ? "0" : entityMap.get("amount").toString());
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				is_check_imme = true;
				
				double do_amount = 0;
				immeKey = entityMap.get("inv_id").toString();
				//先进先出材料列表
				List<Map<String, Object>> fifoList = immeMap.get(immeKey);
				
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
				if (fifoList != null && fifoList.size() > 0) {
					for (Map<String, Object> fifoMap : fifoList) {
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
						if(imme_amount == 0){
							continue; 
						}
						
						//获取invKey
						invKey = entityMap.get("inv_id").toString() +"-"+ fifoMap.get("batch_no").toString() +"-"+ fifoMap.get("bar_code").toString() +"-"+ fifoMap.get("price").toString()+"-";
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
								detailMap.put("mat_type_name", fifoMap.get("mat_type_name"));
								detailMap.put("fac_name", fifoMap.get("fac_name"));
								detailMap.put("batch_no", fifoMap.get("batch_no"));
							//	detailMap.put("batch_sn", fifoMap.get("batch_sn"));
								detailMap.put("bar_code", fifoMap.get("bar_code"));
								detailMap.put("price", fifoMap.get("price"));
								detailMap.put("sale_price", fifoMap.get("sale_price"));
								detailMap.put("sell_price", fifoMap.get("sell_price"));
								detailMap.put("location_id", fifoMap.get("location_id"));
								//detailMap.put("location_code", fifoMap.get("location_code"));
								//detailMap.put("location_name", fifoMap.get("location_name"));
								detailMap.put("location_new_code", fifoMap.get("location_new_code"));
								detailMap.put("location_new_name", fifoMap.get("location_new_name"));
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
									//处理科室需求计划对应关系
									if(entityMap.get("req_id") != null && !"".equals(entityMap.get("req_id").toString()) && do_amount != 0){
										String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
										if("".equals(other_ids)){
											other_ids = entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
										}else{
											other_ids += "," + entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
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
							//处理科室需求计划对应关系
							if(entityMap.get("req_id") != null && !"".equals(entityMap.get("req_id").toString()) && do_amount != 0){
								String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
								if("".equals(other_ids)){
									other_ids = entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
								}else{
									other_ids += "," + entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
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
					
					if(amount > 0 && is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}

					immeMap.put(immeKey, fifoList);
				}else{
					if(is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				}
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存物资不足：<br>"+invEnoughMsg.toString()+"\"}";
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
			Map<String, Map<String, Object>> invMap = new LinkedHashMap<String, Map<String, Object>>();
			
			//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
			Map<String, Map<String, Object>> invJsonMap = new LinkedHashMap<String, Map<String, Object>>();
			
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

			/***获取相关材料库存批号批次条码信息**********begin***************/
			Map<String, Object> queryMap = new HashMap<String, Object>();
			boolean is_frist = true;
			StringBuffer inv_ids = new StringBuffer();
			for(Map<String, Object> entityMap : entityList){
				if(is_frist){
					queryMap.put("group_id", entityMap.get("group_id"));
					queryMap.put("hos_id", entityMap.get("hos_id"));
					queryMap.put("copy_code", entityMap.get("copy_code"));
					queryMap.put("store_id", entityMap.get("store_id"));

					if(entityMap.containsKey("amount")){
						if(Double.parseDouble(entityMap.get("amount").toString()) > 0){
							queryMap.put("flag", "1");
						}else{
							queryMap.put("flag", "0");
						}
					}else{
						queryMap.put("flag", "1");
					}
					inv_ids.append(entityMap.get("inv_id").toString());
					is_frist = false;
				}else{
					inv_ids.append(",").append(entityMap.get("inv_id").toString());
				}
			}
			queryMap.put("inv_id", inv_ids.toString());
			List<Map<String, Object>> queryFifoMap = toListMapLower(matCommonMapper.queryMatAffiFifoByInvList(queryMap));
			for(Map<String, Object> fifoMap : queryFifoMap){
				immeKey = fifoMap.get("inv_id").toString();
				List<Map<String, Object>> list;
				if(immeMap.containsKey(immeKey)){
					list = immeMap.get(immeKey);
				}else{
					list = new ArrayList<Map<String,Object>>();
				}
				list.add(fifoMap);
				
				immeMap.put(immeKey, list);
			}
			/***获取相关材料库存批号批次条码信息**********end*****************/
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				amount = Double.valueOf(entityMap.get("amount") == null ? "0" : entityMap.get("amount").toString());
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				is_check_imme = true;
				
				double do_amount = 0;
				immeKey = entityMap.get("inv_id").toString();
				//先进先出材料列表
				List<Map<String, Object>> fifoList = immeMap.get(immeKey);
				
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
				if (fifoList != null && fifoList.size() > 0) {
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
								detailMap.put("mat_type_name", fifoMap.get("mat_type_name"));
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
					
					if(amount > 0 && is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}

					immeMap.put(immeKey, fifoList);
				}else{
					if(is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				}
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存物资不足：<br>"+invEnoughMsg.toString()+"\"}";
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
				
				List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
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
				return "{\"error\":\"以下材料库存物资不足：<br>"+invEnoughMsg.toString()+"\"}";
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
				List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(mapDetailVo));
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
				return "{\"error\":\"以下材料库存物资不足：<br>"+invEnoughMsg.toString()+"\"}";
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
	public String queryMatInvListDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatInvListDept(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatInvListDept(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryMatParas() throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String, Object>> list = matCommonMapper.queryMatParas(entityMap);
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(Map<String, Object> m : list){
			if("para_04005".equals(m.get("PARA_CODE")) || "para_04006".equals(m.get("PARA_CODE"))){
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
	public String queryMatAffiOutInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatAffiOutInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatAffiOutInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 代销出库材料列表调拨
	 */
	@Override
	public String queryMatAffiTranInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatAffiTranInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatAffiTranInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 代销出库材料列表（有批次）
	 */
	@Override
	public String queryMatAffiOutDetailInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(entityMap.get("inva_date")!=null &&  !"".equals(entityMap.get("inva_date"))) {
			entityMap.put("inva_date", DateUtil.stringToDate( entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("disinfect_date")!=null &&  !"".equals(entityMap.get("disinfect_date"))){
			entityMap.put("disinfect_date", DateUtil.stringToDate( entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
		}
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatAffiOutDetailInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatAffiOutDetailInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 先进先出组装材料批次数据
	 */
	@Override
	public String queryMatInvByFifo(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

			if(entityMap.containsKey("amount")){
				if(Double.parseDouble(entityMap.get("amount").toString()) > 0){
					entityMap.put("flag", "1");
				}else{
					entityMap.put("flag", "0");
				}
			}else{
				entityMap.put("flag", "1");
			}
			
			if(entityMap.get("inva_date") !=null && !"".equals(entityMap.get("inva_date"))){
				entityMap.put("inva_date", DateUtil.stringToDate( entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("disinfect_date")!=null && !"".equals(entityMap.get("disinfect_date"))){
				entityMap.put("disinfect_date", DateUtil.stringToDate( entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
			}
			
			//fifo帐表信息
			List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>)matCommonMapper.queryMatStockInvDetailList(entityMap));
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
			//return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 queryMatOutInvFifo\"}";
		}
	}
	
	/**
	 * 代销  先进先出组装材料批次数据
	 */
	@Override
	public String queryMatAffiInvByFifo(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			if(entityMap.get("inva_date")!=null && !"".equals(entityMap.get("inva_date"))){
				entityMap.put("inva_date", DateUtil.stringToDate( entityMap.get("inva_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("disinfect_date")!=null && !"".equals(entityMap.get("disinfect_date"))){
				entityMap.put("disinfect_date", DateUtil.stringToDate( entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
			}
			
			//fifo帐表信息
			List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>)matCommonMapper.queryMatAffiOutDetailInvList(entityMap));
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
			//return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 queryMatOutInvFifo\"}";
		}
	}
	
	
	
	
	/**
	 * 取出当前物流的未结账最小会计期间
	 */
	@Override
	public Map<String, Object> queryMatCurYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = matCommonMapper.queryMatCurYearMonth(entityMap);
		
		return map;
			}
	/**
	 * 取出当前物流的最大结账期间（即：当前反结账期间）
	 */	
	@Override
	public Map<String, Object> queryMatLastYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map=new HashMap<String,Object>();
		map = matCommonMapper.queryMatLastYearMonth(entityMap);
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
	 * 取出当前物流耐用品的未结账最小会计期间
	 */
	@Override
	public Map<String, Object> queryMatDuraCurYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map_cur = matCommonMapper.queryMatDuraCurYearMonth(entityMap);		
		Map<String,Object> map_last = matCommonMapper.queryMatDuraLastYearMonth(entityMap);
		Map<String,Object> map_start = matCommonMapper.queryMatDuraStartYearMonth(entityMap);
		
		Map<String,Object> cur_map = new HashMap<String,Object>();
		if(map_cur!=null){
			cur_map.put("acc_year",map_cur.get("acc_year"));
			cur_map.put("acc_month",map_cur.get("acc_month"));
		}else if(map_last!=null){
			String cur_year =map_last.get("last_year").toString();
			String cur_month =map_last.get("last_month").toString();
			Integer month=Integer.parseInt(map_last.get("last_month").toString());
			Integer year=Integer.parseInt(map_last.get("last_year").toString());
			if ("12".equals(cur_month)) {
				year =year+1;
				cur_year=year.toString();
				cur_month="01";
			}else{
				month =month+1;
				cur_month=(month.toString().length()==1)?('0'+month.toString()):month.toString();		
			}
			cur_map.put("acc_year",cur_year);
			cur_map.put("acc_month",cur_month);
		}else if(map_start!=null){
			cur_map.put("acc_year",map_start.get("acc_year"));
			cur_map.put("acc_month",map_start.get("acc_month"));
		}
		
		return cur_map;
	}
	/**
	 * 取出当前物流耐用品的最大结账期间（即：当前反结账期间）
	 */	
	@Override
	public Map<String, Object> queryMatDuraLastYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map=new HashMap<String,Object>();
		map = matCommonMapper.queryMatDuraLastYearMonth(entityMap);
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
	public String existsMatStoreIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatStoreIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatStoreIncludeInv\"}";
		}	
	}
	
	
	/**
	 * @Description 判断是否有条码存在
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMatSnInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String sn = matCommonMapper.existsMatSnInv(entityMap);
			if(sn == null || "0".equals(sn)|| "".equals(sn)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"sn\":\""+sn+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatSnInv\"}";
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
	public String existsMatSupIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatSupIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatStoreIncludeInv\"}";
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
	public String existsMatInvOnlySup(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatInvOnlySup(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvOnlySup\"}";
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
	public String existsMatAffiSupIncludeInv(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatAffiSupIncludeInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatStoreIncludeInv\"}";
		}	
	}
	
	/**
	 * @Description 判断该供应商下的材料是否有库存
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String existsMatAffiSupIncludeInvAmount(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatAffiSupIncludeInvAmount(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatStoreIncludeInv\"}";
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
	public String existsMatAffiInvOnlySup(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inv_ids = matCommonMapper.existsMatAffiInvOnlySup(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvOnlySup\"}";
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
	public String queryMatInvBatchInva(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String inva_date = DateUtil.dateToString(matCommonMapper.queryMatInvBatchInva(entityMap), "yyyy-MM-dd");
			if(inva_date != null && !"".equals(inva_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("inva_date"))) && !inva_date.equals(DateUtil.jsDateToString(entityMap.get("inva_date").toString(), "yyyy-MM-dd"))){
				return "{\"state\":\"false\",\"inva_date\":\""+inva_date+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchInva\"}";
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
	public String queryMatInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			String disinfect_date = DateUtil.dateToString(matCommonMapper.queryMatInvBatchDisinfect(entityMap), "yyyy-MM-dd");
			if(disinfect_date != null && !"".equals(disinfect_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("disinfect_date"))) && !disinfect_date.equals(DateUtil.jsDateToString(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"))){
				return "{\"state\":\"false\",\"disinfect_date\":\""+disinfect_date+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchDisinfect\"}";
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
	public String queryMatInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			Double price = null;
			if(entityMap.get("type") != null && "affi".equals(entityMap.get("type").toString().toLowerCase())){
				//代销
				price = matCommonMapper.queryMatAffiInvBatchPrice(entityMap);
			}else{
				//非代销
				price = matCommonMapper.queryMatInvBatchPrice(entityMap);
			}
			if(price != null && !"".equals(price.toString()) && ChdJson.validateJSON(String.valueOf(entityMap.get("price"))) && NumberUtil.sub(price, Double.valueOf(entityMap.get("price").toString())) != 0){
				return "{\"state\":\"false\",\"price\":\""+price+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchPrice\"}";
		}	
		return "{\"state\":\"true\"}";
	}
	/**
	 * 生成table_code表的下一个单号（基本适用所有业务）
	 * @param entityMap
	 * @return
	 */
	@Override
	public synchronized String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException{
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
		//单号长度
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
			store_alias = matCommonMapper.queryStoreAliasById(map);
		}
		//取业务类型简称type_flag
		String bus_type = "";
		if(ishave_bus){
			bus_type = matCommonMapper.queryBusTypeFlagByCode(map);
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
			containsDay = MyConfig.getSysPara("04037");
		}
		//不按日生成单据号则Map中移除日期并单据流水长度+2
		if("0".equals(containsDay)){
			map.remove("day");
			entityMap.remove("day");
			if(change_length){
				length_no = length_no + 2;
			}
		}
		
		//表里day字段默认为00（由于增加系统参数04037导致非月初更改参数会造成当月存在多条数据而生成单号报错）
		map.put("day", map.get("day") == null ? "00" : map.get("day"));
		
		//判断是否存在该业务流水码
		int flag = matNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			matNoManageMapper.add(map);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(map);
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
		
		List<?> list = matCommonMapper.queryInvPicture(entityMap);
			
		return ChdJson.toJson(list);
	}

	
	/**
	 * 采购计划  材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatInvListByPur(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matCommonMapper.queryMatInvListByPur(entityMap);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matCommonMapper.queryMatInvListByPur(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
	}
	
	@Override
	public String queryMatCertDate(Map<String,Object> entityMap) throws DataAccessException{
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date() ;
			entityMap.put("currentDate", df.format(currentDate));
			Map<String,Object> certDate = matCommonMapper.queryMatCertDate(entityMap);
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
		
		return JsonListMapUtil.toMapLower(matCommonMapper.queryLoginUserMsg(entityMap));
	}

	@Override
	public String queryMatInvListIn(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatInvListIn(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatInvListIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
   }

	/**
	 * 材料期初入库
	 */
	@Override
	public String queryMatInvListInitial(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatInvListInitial(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatInvListInitial(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
   }
	
	
	@Override
	public String existsMatAffiSupIsInv(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = matCommonMapper.existsMatAffiSupIsInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvOnlySup\"}";
		}
	}

	@Override
	public String existsMatInSupIsInv(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = matCommonMapper.existsMatInSupIsInv(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				inv_ids = inv_ids.replace("\\", "\\\\");
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvOnlySup\"}";
		}
	}
	
	/**
	 * @Description 耐用品材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDuraInvList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatDuraInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatDuraInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 耐用品库房材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatDuraStoreInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatDuraStoreInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 耐用品库房材料条码字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDuraStoreInvBarList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatDuraStoreInvBarList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatDuraStoreInvBarList(entityMap, rowBounds);
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
	public String queryMatDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matCommonMapper.queryMatDuraDeptInvList(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matCommonMapper.queryMatDuraDeptInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	
	
	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDuraDeptInvListNew(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matCommonMapper.queryMatDuraDeptInvListNew(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matCommonMapper.queryMatDuraDeptInvListNew(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 耐用品科室材料条码字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDuraDeptInvBarList(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonMapper.queryMatDuraDeptInvBarList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonMapper.queryMatDuraDeptInvBarList(entityMap, rowBounds);
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
	public String queryMatOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException{
		String flag = "1";
		if(entityMap.get("flag") != null && "".equals(entityMap.get("flag").toString())){
			flag = entityMap.get("flag").toString();
		}
		
		if("1".equals(flag)){
			//获取材料ID（检验条码是否存在）
			Long inv_id = matCommonMapper.existsMatOutInvListByBar(entityMap);
			if(inv_id != null){
				entityMap.put("inv_id", inv_id);
				List<Map<String, Object>> list = matCommonMapper.queryMatOutInvListByBar(entityMap);
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
			Long inv_id = matCommonMapper.existsMatOutInvListByBar(map);
			if(inv_id != null){
				//获取结束条码对应的材料ID（检验条码是否存在）
				map.put("store_id", entityMap.get("bar_code_end"));
				Long inv_id_end = matCommonMapper.existsMatOutInvListByBar(map);
				if(inv_id_end != null){
					//校验开始条码与结束条码对应的材料是否一致
					if(inv_id == inv_id_end){
						entityMap.put("inv_id", inv_id);
						List<Map<String, Object>> list = matCommonMapper.queryMatOutInvListByBar(entityMap);
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
	public String queryMatAffiOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException{
		String flag = "1";
		if(entityMap.get("flag") != null && "".equals(entityMap.get("flag").toString())){
			flag = entityMap.get("flag").toString();
		}
		
		if("1".equals(flag)){
			//获取材料ID（检验条码是否存在）
			Long inv_id = matCommonMapper.existsMatAffiOutInvListByBar(entityMap);
			if(inv_id != null){
				entityMap.put("inv_id", inv_id);
				List<Map<String, Object>> list = matCommonMapper.queryMatAffiOutInvListByBar(entityMap);
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
			Long inv_id = matCommonMapper.existsMatAffiOutInvListByBar(map);
			if(inv_id != null){
				//获取结束条码对应的材料ID（检验条码是否存在）
				map.put("store_id", entityMap.get("bar_code_end"));
				Long inv_id_end = matCommonMapper.existsMatAffiOutInvListByBar(map);
				if(inv_id_end != null){
					//校验开始条码与结束条码对应的材料是否一致
					if(inv_id == inv_id_end){
						entityMap.put("inv_id", inv_id);
						List<Map<String, Object>> list = matCommonMapper.queryMatAffiOutInvListByBar(entityMap);
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
	public String queryMatStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		System.out.println(entityMap.get("invMsg"));
		
		//---开始---东阳需求,已经显示的材料,材料选择列表不再显示
		if (entityMap.get("invMsg")!=null && StringUtils.isNotBlank(entityMap.get("invMsg").toString())) {
			String[] invMsgs = entityMap.get("invMsg").toString().split("@");
			entityMap.put("invMsg", invMsgs);
		}
		//---结束---东阳需求,已经显示的材料,材料选择列表不再显示
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListBackNew(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatStockInvListBackNew(entityMap, rowBounds);
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
	public String queryMatDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatDeptStockInvList(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatDeptStockInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public String queryMatStoreByCode(Map<String, Object> mapVo)
			throws DataAccessException {
		
		try {	
			Map<String, Object> map=matStoreMapper.queryByCode(mapVo);
			if(map == null || "".equals(map)){
				return "{\"state\":\"true\",\"is_control\":\"0\"}";
			}else{
				return "{\"state\":\"true\",\"is_control\":\""+map.get("is_control").toString()+"\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatStoreByCode\"}";
		}
		
		
	}
	//  代銷科室退库
	@Override
	public String queryMatAffiOutBackInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatAffiOutBackInvList(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonMapper.queryMatAffiOutBackInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public String getInvaTimeByBatchNo(Map<String, Object> page) {
		Map<String, Object> map = (Map<String, Object>) matCommonMapper.getInvaTimeByBatchNo(page);
		if (null==map) {
			map=new HashMap<String, Object>();
			map.put("inva_date", "");
		} 
		return ChdJson.toJson(map);
	}

	/**
	 * 批量判断是否存在不等于该状态的单据(t_id需与entityList中主键ID一直，t_id_value与entityList至少一个有值)
	 * @param entityList
	 * @return
	 */
	public Integer existsStateBatch(String table, String t_id, String t_id_value, String check_state, String check_state_value, 
			List<Map<String, Object>> entityList) throws DataAccessException{

		Map<String, Object> checkMap = new HashMap<String, Object>();
		//集团ID
		checkMap.put("group_id", SessionManager.getGroupId());
		//医院ID
		checkMap.put("hos_id", SessionManager.getHosId());
		//账套编码
		checkMap.put("copy_code", SessionManager.getCopyCode());
		//表名称
		checkMap.put("table", table);
		//主键ID字段
		checkMap.put("t_id", t_id);
		//主键ID值
		StringBuffer t_ids = new StringBuffer();
		t_ids.append(t_id_value == null ? "" : t_id_value).append(",");
		if(entityList != null && entityList.size() > 0){
			for(Map<String, Object> map : entityList){
				t_ids.append(map.get(t_id) == null ? "" : map.get(t_id).toString()).append(",");
			}
		}
		String ids = t_ids.toString();
		ids = ids.replace(",,", ",");
		if(ids.startsWith(",")){
			ids = ids.substring(1, ids.length());
		} 
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		checkMap.put("t_id_value", ids);
		//需校验的状态字段
		checkMap.put("check_state", check_state);
		//状态字段值
		checkMap.put("check_state_value", check_state_value);
		
		return matCommonMapper.existsStateBatch(checkMap);
	}
	/**
	 * 科室配套表筛选材料
	 */
	@Override
	public String queryMatInvListByMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matCommonMapper.queryMatInvListByMatch(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matCommonMapper.queryMatInvListByMatch(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
	}
	
	/**
	 * 用于组装采购发票明细json格式
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override   
	public String getDetailJsonByDetailListBill(List<Map<String, Object>> entityList) throws DataAccessException{
		
		//组装明细结果集
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		//入库单Map
		Map<String, Map<String, Object>> inMap = new LinkedHashMap<String, Map<String, Object>>();
		//用于合并相同入库单的材料明细信息
		Map<String, Map<String, Object>> inJsonMap = new LinkedHashMap<String, Map<String, Object>>();
		
		try{
			String inKey = "";  //材料Map的key值
			String inJsonKey = "";  //材料JsonMap的key值
			int state = 0;  //单据状态
			Double amount = 0.0;  //入库数量
			Double out_amount = 0.0;  //出库数量
			Double amount_money = 0.0;  //金额
			Double not_bill_money = 0.0;  //未开票金额
			Double had_bill_money = 0.0;  //已开票金额
			Double bill_money = 0.0;  //发票金额
			Double payable_money = 0.0;  //已付款金额
			boolean check_row = false;
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				//初始化变量
				check_row = false;

				//获取invKey
				//ID无限大后有可能前面相同所以加a
				inKey =entityMap.get("in_id").toString()+"AA";
				inJsonKey =entityMap.get("in_id").toString()+"AA"+entityMap.get("in_detail_id").toString()+"BB";
				
				amount = Double.valueOf(entityMap.get("amount").toString());
				if(entityMap.get("out_amount") != null && !"".equals(entityMap.get("out_amount").toString())){
					out_amount = Double.valueOf(entityMap.get("out_amount").toString());
				}else{
					out_amount = 0.0;
				}
				amount_money = Double.valueOf(entityMap.get("amount_money").toString());
				if(entityMap.get("not_bill_money") != null && !"".equals(entityMap.get("not_bill_money").toString())){
					not_bill_money = Double.valueOf(entityMap.get("not_bill_money").toString());
				}else{
					//发票修改页面不需要查询出该字段，所以这里默认为0
					not_bill_money = 0.0;
				}
				if(entityMap.get("had_bill_money") != null && !"".equals(entityMap.get("had_bill_money").toString())){
					had_bill_money = Double.valueOf(entityMap.get("had_bill_money").toString());
				}else{
					//发票修改页面不需要查询出该字段，所以这里默认为0
					had_bill_money = 0.0;
				}
				if(entityMap.get("bill_money") != null && !"".equals(entityMap.get("bill_money").toString())){
					bill_money = Double.valueOf(entityMap.get("bill_money").toString());
				}else{
					bill_money = 0.0;
				}
				if(entityMap.get("payable_money") != null && !"".equals(entityMap.get("payable_money").toString())){
					payable_money = Double.valueOf(entityMap.get("payable_money").toString());
				}else{
					payable_money = 0.0;
				}
				
				if(entityMap.get("check_row") != null && "1".equals(entityMap.get("check_row").toString())){
					check_row = true;
				}

				//用于存储信息
				Map<String, Object> detailMap = new HashMap<String, Object>();
				Map<String, Object> detailJsonMap = new HashMap<String, Object>();
				//判断材料是否存在
				if(inMap.containsKey(inKey)){
					/*********组装明细主信息********begin********/
					//获取已有的detailMap
					detailMap = inMap.get(inKey);
					//累加数量、金额
					detailMap.put("amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));
					detailMap.put("out_amount", NumberUtil.add(Double.valueOf(detailMap.get("out_amount").toString()), out_amount));
					detailMap.put("amount_money", NumberUtil.add(Double.valueOf(detailMap.get("amount_money").toString()), amount_money));
					detailMap.put("not_bill_money", NumberUtil.add(Double.valueOf(detailMap.get("not_bill_money").toString()), not_bill_money));
					detailMap.put("had_bill_money", NumberUtil.add(Double.valueOf(detailMap.get("had_bill_money").toString()), had_bill_money));
					if(check_row){
						detailMap.put("bill_money", NumberUtil.add(Double.valueOf(detailMap.get("bill_money").toString()), bill_money));
						detailMap.put("payable_money", NumberUtil.add(Double.valueOf(detailMap.get("payable_money").toString()), payable_money));
					}
					
					inMap.put(inKey, detailMap);
					/*********组装明细主信息********end**********/
					/*********组装明细批次信息******begin********/
					detailJsonMap.put("in_id", entityMap.get("in_id"));
					detailJsonMap.put("in_detail_id", entityMap.get("in_detail_id"));
					detailJsonMap.put("bill_id", entityMap.get("bill_id"));
					detailJsonMap.put("bill_detail_id", entityMap.get("bill_detail_id"));
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("inv_model", entityMap.get("inv_model"));
					detailJsonMap.put("amount", amount);
					detailJsonMap.put("out_amount", out_amount);
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("batch_no", entityMap.get("batch_no"));
					detailJsonMap.put("bar_code", entityMap.get("bar_code"));
					detailJsonMap.put("unit_name", entityMap.get("unit_name"));
					detailJsonMap.put("bill_no", entityMap.get("bill_no"));
					detailJsonMap.put("check_row", entityMap.get("check_row"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("not_bill_money", not_bill_money);
					detailJsonMap.put("had_bill_money", had_bill_money);
					detailJsonMap.put("bill_money", bill_money);
					detailJsonMap.put("payable_money", payable_money);
				
					inJsonMap.put(inJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
				}else{
					/*********组装明细批次信息******begin********/
					detailJsonMap.put("in_id", entityMap.get("in_id"));
					detailJsonMap.put("in_detail_id", entityMap.get("in_detail_id"));
					detailJsonMap.put("bill_id", entityMap.get("bill_id"));
					detailJsonMap.put("bill_detail_id", entityMap.get("bill_detail_id"));
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("inv_model", entityMap.get("inv_model"));
					detailJsonMap.put("amount", amount);
					detailJsonMap.put("out_amount", out_amount);
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("batch_no", entityMap.get("batch_no"));
					detailJsonMap.put("bar_code", entityMap.get("bar_code"));
					detailJsonMap.put("unit_name", entityMap.get("unit_name"));
					detailJsonMap.put("bill_no", entityMap.get("bill_no"));
					detailJsonMap.put("check_row", entityMap.get("check_row"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("not_bill_money", not_bill_money);
					detailJsonMap.put("had_bill_money", had_bill_money);
					detailJsonMap.put("bill_money", bill_money);
					detailJsonMap.put("payable_money", payable_money);
					
					inJsonMap.put(inJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
					/*********组装明细主信息********begin********/
					detailMap = new HashMap<String,Object>();
					detailMap.put("in_id", entityMap.get("in_id"));
					detailMap.put("in_no", entityMap.get("in_no"));
					detailMap.put("bus_type_name", entityMap.get("bus_type_name"));
					detailMap.put("store_id", entityMap.get("store_id"));//该字段用于页面判断，不可去掉
					detailMap.put("store_name", entityMap.get("store_name"));
					detailMap.put("sup_name", entityMap.get("sup_name"));
					detailMap.put("in_date", entityMap.get("in_date"));
					detailMap.put("maker_name", entityMap.get("maker_name"));
					detailMap.put("brief", entityMap.get("brief"));
					detailMap.put("confirm_date", entityMap.get("confirm_date"));
					detailMap.put("confirmer_name", entityMap.get("confirmer_name"));
					detailMap.put("main_bill_no", entityMap.get("main_bill_no"));
					detailMap.put("bill_state", entityMap.get("bill_state"));
					detailMap.put("bill_state_name", entityMap.get("bill_state_name"));
					//重新对金额复制，防止出现null值导致报错
					detailMap.put("amount", amount);
					detailMap.put("out_amount", out_amount);
					detailMap.put("amount_money", amount_money);
					detailMap.put("not_bill_money", not_bill_money);
					detailMap.put("had_bill_money", had_bill_money);
					if(check_row){
							detailMap.put("bill_money", bill_money);
							detailMap.put("payable_money", payable_money);
					}else{
						detailMap.put("bill_money", 0);
						detailMap.put("payable_money", 0);
					}
					
					inMap.put(inKey, detailMap);
					/*********组装明细主信息********end**********/
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : inMap.keySet()) {
				Map<String, Object> detailMap = inMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : inJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = inJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				} 
				detailJson.append("]");
				detailMap.put("detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
		return ChdJson.toJson(detailList);
	}
	
	/**
	 * 用于组装付款单明细json格式
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override
	public String getDetailJsonByDetailListPay(List<Map<String, Object>> entityList) throws DataAccessException{
		
		//组装明细结果集
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		//发票Map
		Map<String, Map<String, Object>> billMap = new LinkedHashMap<String, Map<String, Object>>();
		//用于合并相同发票的明细信息
		Map<String, Map<String, Object>> billJsonMap = new LinkedHashMap<String, Map<String, Object>>();
		
		try{
			String billKey = "";  //材料Map的key值
			String billJsonKey = "";  //材料JsonMap的key值
			int state = 0;  //单据状态
			Double amount = 0.0;  //入库数量
			Double amount_money = 0.0;  //金额
			Double bill_money = 0.0;  //发票金额
			Double not_pay_money = 0.0;  //未付款金额
			Double had_dis_money = 0.0;  //已优惠金额
			Double had_pay_money = 0.0;  //已付款金额
			Double dis_money = 0.0;  //优惠金额
			Double payable_money = 0.0;  //已付金额1
			Double payed_money = 0.0;  //已付金额2
			Double pay_money = 0.0;  //付款金额
			boolean check_row = false;
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				//初始化变量
				check_row = false;

				//获取invKey
				//ID无限大后有可能前面相同所以加a
				billKey =entityMap.get("bill_id").toString()+"AA";
				billJsonKey =entityMap.get("bill_id").toString()+"AA"+entityMap.get("bill_detail_id").toString()+"BB";
				
				amount = Double.valueOf(entityMap.get("amount").toString());
				if(entityMap.get("amount_money") != null && !"".equals(entityMap.get("amount_money"))){
					amount_money = Double.valueOf(entityMap.get("amount_money").toString());
				}else{
					amount_money = 0.0;
				}
				if(entityMap.get("not_pay_money") != null && !"".equals(entityMap.get("not_pay_money").toString())){
					not_pay_money = Double.valueOf(entityMap.get("not_pay_money").toString());
				}else{
					//发票修改页面不需要查询出该字段，所以这里默认为0
					not_pay_money = 0.0;
				}
				if(entityMap.get("had_dis_money") != null && !"".equals(entityMap.get("had_dis_money").toString())){
					had_dis_money = Double.valueOf(entityMap.get("had_dis_money").toString());
				}else{
					//发票修改页面不需要查询出该字段，所以这里默认为0
					had_dis_money = 0.0;
				}
				if(entityMap.get("had_pay_money") != null && !"".equals(entityMap.get("had_pay_money").toString())){
					had_pay_money = Double.valueOf(entityMap.get("had_pay_money").toString());
				}else{
					//发票修改页面不需要查询出该字段，所以这里默认为0
					had_pay_money = 0.0;
				}
				if(entityMap.get("bill_money") != null && !"".equals(entityMap.get("bill_money").toString())){
					bill_money = Double.valueOf(entityMap.get("bill_money").toString());
				}else{
					bill_money = 0.0;
				}
				if(entityMap.get("payable_money") != null && !"".equals(entityMap.get("payable_money").toString())){
					payable_money = Double.valueOf(entityMap.get("payable_money").toString());
				}else{
					payable_money = 0.0;
				}
				if(entityMap.get("payed_money") != null && !"".equals(entityMap.get("payed_money").toString())){
					payed_money = Double.valueOf(entityMap.get("payed_money").toString());
				}else{
					payed_money = 0.0;
				}
				if(entityMap.get("dis_money") != null && !"".equals(entityMap.get("dis_money").toString())){
					dis_money = Double.valueOf(entityMap.get("dis_money").toString());
				}else{
					dis_money = 0.0;
				}
				if(entityMap.get("pay_money") != null && !"".equals(entityMap.get("pay_money").toString())){
					pay_money = Double.valueOf(entityMap.get("pay_money").toString());
				}else{
					pay_money = 0.0;
				}
				
				if(entityMap.get("check_row") != null && "1".equals(entityMap.get("check_row").toString())){
					check_row = true;
				}

				//用于存储信息
				Map<String, Object> detailMap = new HashMap<String, Object>();
				Map<String, Object> detailJsonMap = new HashMap<String, Object>();
				//判断材料是否存在
				if(billMap.containsKey(billKey)){
					/*********组装明细主信息********begin********/
					//获取已有的detailMap
					detailMap = billMap.get(billKey);
					//累加数量、金额
					detailMap.put("amount", NumberUtil.add(Double.valueOf(detailMap.get("amount").toString()), amount));
					detailMap.put("amount_money", NumberUtil.add(Double.valueOf(detailMap.get("amount_money").toString()), amount_money));
					detailMap.put("bill_money", NumberUtil.add(Double.valueOf(detailMap.get("bill_money").toString()), bill_money));
					detailMap.put("not_pay_money", NumberUtil.add(Double.valueOf(detailMap.get("not_pay_money").toString()), not_pay_money));
					detailMap.put("had_dis_money", NumberUtil.add(Double.valueOf(detailMap.get("had_dis_money").toString()), had_dis_money));
					detailMap.put("had_pay_money", NumberUtil.add(Double.valueOf(detailMap.get("had_pay_money").toString()), had_pay_money));
					if(check_row){
						detailMap.put("payable_money", NumberUtil.add(Double.valueOf(detailMap.get("payable_money").toString()), payable_money));
						detailMap.put("payed_money", NumberUtil.add(Double.valueOf(detailMap.get("payed_money").toString()), payed_money));
						detailMap.put("pay_money", NumberUtil.add(Double.valueOf(detailMap.get("pay_money").toString()), pay_money));
						detailMap.put("dis_money", NumberUtil.add(Double.valueOf(detailMap.get("dis_money").toString()), dis_money));
					}
					
					billMap.put(billKey, detailMap);
					/*********组装明细主信息********end**********/
					/*********组装明细批次信息******begin********/
					detailJsonMap.put("pay_id", entityMap.get("pay_id"));
					detailJsonMap.put("pay_detail_id", entityMap.get("pay_detail_id"));
					detailJsonMap.put("bill_id", entityMap.get("bill_id"));
					detailJsonMap.put("bill_detail_id", entityMap.get("bill_detail_id"));
					detailJsonMap.put("in_no", entityMap.get("in_no"));
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("inv_model", entityMap.get("inv_model"));
					detailJsonMap.put("amount", amount);
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("batch_no", entityMap.get("batch_no"));
					detailJsonMap.put("bar_code", entityMap.get("bar_code"));
					detailJsonMap.put("unit_name", entityMap.get("unit_name"));
					detailJsonMap.put("check_row", entityMap.get("check_row"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("bill_money", bill_money);
					detailJsonMap.put("not_pay_money", not_pay_money);
					detailJsonMap.put("had_dis_money", had_dis_money);
					detailJsonMap.put("had_pay_money", had_pay_money);
					detailJsonMap.put("payable_money", payable_money);
					detailJsonMap.put("payed_money", payed_money);
					detailJsonMap.put("dis_money", dis_money);
					detailJsonMap.put("pay_money", pay_money);
				
					billJsonMap.put(billJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
				}else{
					/*********组装明细批次信息******begin********/
					detailJsonMap.put("pay_id", entityMap.get("pay_id"));
					detailJsonMap.put("pay_detail_id", entityMap.get("pay_detail_id"));
					detailJsonMap.put("bill_id", entityMap.get("bill_id"));
					detailJsonMap.put("bill_detail_id", entityMap.get("bill_detail_id"));
					detailJsonMap.put("in_no", entityMap.get("in_no"));
					detailJsonMap.put("inv_id", entityMap.get("inv_id"));
					detailJsonMap.put("inv_no", entityMap.get("inv_no"));
					detailJsonMap.put("inv_code", entityMap.get("inv_code"));
					detailJsonMap.put("inv_name", entityMap.get("inv_name"));
					detailJsonMap.put("inv_model", entityMap.get("inv_model"));
					detailJsonMap.put("amount", amount);
					detailJsonMap.put("price", entityMap.get("price"));
					detailJsonMap.put("batch_no", entityMap.get("batch_no"));
					detailJsonMap.put("bar_code", entityMap.get("bar_code"));
					detailJsonMap.put("unit_name", entityMap.get("unit_name"));
					detailJsonMap.put("bill_no", entityMap.get("bill_no"));
					detailJsonMap.put("check_row", entityMap.get("check_row"));
					detailJsonMap.put("amount_money", amount_money);
					detailJsonMap.put("bill_money", bill_money);
					detailJsonMap.put("not_pay_money", not_pay_money);
					detailJsonMap.put("had_dis_money", had_dis_money);
					detailJsonMap.put("had_pay_money", had_pay_money);
					detailJsonMap.put("payable_money", payable_money);
					detailJsonMap.put("payed_money", payed_money);
					detailJsonMap.put("dis_money", dis_money);
					detailJsonMap.put("pay_money", pay_money);
					
					billJsonMap.put(billJsonKey, detailJsonMap);
					/*********组装明细批次信息******end**********/
					/*********组装明细主信息********begin********/
					detailMap = new HashMap<String,Object>();
					detailMap.put("bill_id", entityMap.get("bill_id"));
					detailMap.put("bill_code", entityMap.get("bill_code"));
					detailMap.put("bill_no", entityMap.get("bill_no"));
					detailMap.put("store_id", entityMap.get("store_id"));//该字段用于页面判断，不可去掉
					detailMap.put("store_name", entityMap.get("store_name"));
					detailMap.put("sup_name", entityMap.get("sup_name"));
					detailMap.put("bill_date", entityMap.get("bill_date"));
					detailMap.put("maker_name", entityMap.get("maker_name"));
					detailMap.put("note", entityMap.get("note"));
					detailMap.put("chk_date", entityMap.get("chk_date"));
					detailMap.put("checker_name", entityMap.get("checker_name"));
					detailMap.put("bill_state_name", entityMap.get("bill_state_name"));
					//重新对金额复制，防止出现null值导致报错
					detailMap.put("amount", amount);
					detailMap.put("amount_money", amount_money);
					detailMap.put("bill_money", bill_money);
					detailMap.put("not_pay_money", not_pay_money);
					detailMap.put("had_dis_money", had_dis_money);
					detailMap.put("had_pay_money", had_pay_money);
					detailMap.put("payable_money", payable_money);
					detailMap.put("payed_money", payed_money);
					if(check_row){
						detailMap.put("dis_money", dis_money);
						detailMap.put("pay_money", pay_money);
					}else{
						detailMap.put("dis_money", 0);
						detailMap.put("pay_money", 0);
					}
					
					billMap.put(billKey, detailMap);
					/*********组装明细主信息********end**********/
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : billMap.keySet()) {
				Map<String, Object> detailMap = billMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : billJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = billJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				} 
				detailJson.append("]");
				detailMap.put("detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
		return ChdJson.toJson(detailList);
	}
	
	
	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	@Override   
	public String getInvJsonByInvListInBack(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			//组装明细结果集
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
			Map<String, Map<String, Object>> invMap = new LinkedHashMap<String, Map<String, Object>>();
			
			//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
			Map<String, Map<String, Object>> invJsonMap = new LinkedHashMap<String, Map<String, Object>>();
			
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

			/***获取相关材料库存批号批次条码信息**********begin***************/
			Map<String, Object> queryMap = new HashMap<String, Object>();
			boolean is_frist = true;
			StringBuffer inv_ids = new StringBuffer();
			for(Map<String, Object> entityMap : entityList){
				if(is_frist){
					queryMap.put("group_id", entityMap.get("group_id"));
					queryMap.put("hos_id", entityMap.get("hos_id"));
					queryMap.put("copy_code", entityMap.get("copy_code"));
					queryMap.put("store_id", entityMap.get("store_id"));

					if(entityMap.containsKey("amount")){
						if(Double.parseDouble(entityMap.get("amount").toString()) > 0){
							queryMap.put("flag", "1");
						}else{
							queryMap.put("flag", "0");
						}
					}else{
						queryMap.put("flag", "1");
					}
					inv_ids.append(entityMap.get("inv_id").toString());
					is_frist = false;
				}else{
					inv_ids.append(",").append(entityMap.get("inv_id").toString());
				}
			}
			queryMap.put("inv_ids", inv_ids.toString());
			List<Map<String, Object>> queryFifoMap = toListMapLower(matCommonMapper.queryMatFifoByInvList(queryMap));
			for(Map<String, Object> fifoMap : queryFifoMap){
				immeKey = fifoMap.get("inv_id").toString()+"-"+ fifoMap.get("batch_no").toString()+"-"+ fifoMap.get("bar_code").toString()+"-"+ fifoMap.get("price").toString()+'-';
				List<Map<String, Object>> list;
				if(immeMap.containsKey(immeKey)){
					list = immeMap.get(immeKey);
				}else{
					list = new ArrayList<Map<String,Object>>();
				}
				list.add(fifoMap);
				
				immeMap.put(immeKey, list);
			}
			/***获取相关材料库存批号批次条码信息**********end*****************/
			
			/********************循环材料基础信息*************************/
			for(Map<String, Object> entityMap : entityList){
				amount = Double.valueOf(entityMap.get("amount") == null ? "0" : entityMap.get("amount").toString());
				//如果数量为0则不获取该材料
				if(amount == 0){
					continue;
				}
				is_check_imme = true;
				
				double do_amount = 0;
				immeKey = entityMap.get("inv_id").toString()+"-"+ entityMap.get("batch_no").toString()+"-"+ entityMap.get("bar_code").toString()+"-"+ entityMap.get("price").toString()+'-';
				//先进先出材料列表
				List<Map<String, Object>> fifoList = immeMap.get(immeKey);
				
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
				if (fifoList != null && fifoList.size() > 0) {
					for (Map<String, Object> fifoMap : fifoList) {
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
						if(imme_amount == 0){
							continue; 
						}
						
						//获取invKey
						invKey = entityMap.get("inv_id").toString() +"-"+ fifoMap.get("batch_no").toString() +"-"+ fifoMap.get("bar_code").toString() +"-"+ fifoMap.get("price").toString()+"-";
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
								detailMap.put("mat_type_name", fifoMap.get("mat_type_name"));
								detailMap.put("fac_name", fifoMap.get("fac_name"));
								detailMap.put("batch_no", fifoMap.get("batch_no"));
							//	detailMap.put("batch_sn", fifoMap.get("batch_sn"));
								detailMap.put("bar_code", fifoMap.get("bar_code"));
								detailMap.put("price", fifoMap.get("price"));
								detailMap.put("sale_price", fifoMap.get("sale_price"));
								detailMap.put("sell_price", fifoMap.get("sell_price"));
								detailMap.put("location_id", fifoMap.get("location_id"));
								//detailMap.put("location_code", fifoMap.get("location_code"));
								//detailMap.put("location_name", fifoMap.get("location_name"));
								detailMap.put("location_new_code", fifoMap.get("location_new_code"));
								detailMap.put("location_new_name", fifoMap.get("location_new_name"));
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
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) - amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) - amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));

								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) - amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) - amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								fifoMap.put("imme_amount", NumberUtil.sub(imme_amount, amount));
								do_amount += amount;  //实际处理数量
								amount = 0.0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) - imme_amount);
								detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) - imme_amount);
								detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
								detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) - imme_amount);
								detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
								detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
								detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) - imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
								
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
									//处理科室需求计划对应关系
									if(entityMap.get("req_id") != null && !"".equals(entityMap.get("req_id").toString()) && do_amount != 0){
										String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
										if("".equals(other_ids)){
											other_ids = entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
										}else{
											other_ids += "," + entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
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
							//处理科室需求计划对应关系
							if(entityMap.get("req_id") != null && !"".equals(entityMap.get("req_id").toString()) && do_amount != 0){
								String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
								if("".equals(other_ids)){
									other_ids = entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
								}else{
									other_ids += "," + entityMap.get("req_id").toString() + "@" + entityMap.get("req_detail_id").toString() + "@" + do_amount;
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
					
					if(amount > 0 && is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}

					immeMap.put(immeKey, fifoList);
				}else{
					if(is_check_imme){
						invEnoughMsg.append(entityMap.get("inv_code")).append(" ").append(entityMap.get("inv_name")).append("<br>");
					}
				}
			}
			
			int v_index = 0;
			int vv_index = 0;
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				v_index = 0;
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						if(v_index > 0){
							detailJson.append(",");
						}
						detailJson.append("{");
						vv_index = 0;
						for(Map.Entry<String, Object> m : detailJsonMap.entrySet()){
							if(vv_index > 0){
								detailJson.append(",");
							}
							detailJson.append("\"").append(m.getKey()).append("\":\"").append(m.getValue() == null ? "" : m.getValue()).append("\"");
							vv_index ++;
						}
						detailJson.append("}");
						v_index ++;
					}
				}
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString().replace("\\", "\\\\"));
				detailList.add(detailMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\"以下材料库存物资不足：<br>"+invEnoughMsg.toString()+"\"}";
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
	public String getMatPactFkxyInfo(Map<String, Object> mapVo) {
		Map<String, Object> map = (Map<String, Object>) matCommonMapper.getMatPactFkxyInfo(mapVo);
		if (null==map) {
			map=new HashMap<String, Object>();
			map.put("pactFkxyInfo", "");
			map.put("is_price_cont", "0");
			map.put("is_total_cont", "0");
		} 
		return ChdJson.toJson(map);
		
	}
}
