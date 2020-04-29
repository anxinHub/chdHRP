package com.chd.hrp.mat.serviceImpl.order.init;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.tool.HttpClientTool;
import com.chd.base.tool.ServerUrl;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.order.MatOrderDetailMapper;
import com.chd.hrp.mat.dao.order.MatOrderMainMapper;
import com.chd.hrp.mat.dao.order.init.MatOrderInitMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.order.init.MatOrderInitService;
import com.chd.hrp.platform.dao.ZjMapper;
import com.github.pagehelper.PageInfo;

@Service("matOrderInitService")
public class MatOrderInitServiceImpl implements MatOrderInitService {  
	private static Logger logger = Logger.getLogger(MatOrderInitServiceImpl.class);   
	
	//引入dao 
	@Resource(name = "matOrderMainMapper")
	private final MatOrderMainMapper matOrderMainMapper = null;
	
	@Resource(name = "matOrderDetailMapper") 
	private final MatOrderDetailMapper matOrderDetailMapper = null;   
	
	@Resource(name = "matOrderInitMapper")
	private final MatOrderInitMapper matOrderInitMapper = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;   
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "zjMapper")
	private final ZjMapper zjMapper = null;
	
	// 返回用用于保存的默认值
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
			
			if (mapVo.get("order_date") == null) {
				mapVo.put("order_date", "");
			}
			
			if (mapVo.get("pur_type") == null) {
				mapVo.put("pur_type", "");
			}

			if (mapVo.get("order_type") == null) {
				mapVo.put("order_type", "");
			}

			if (mapVo.get("brif") == null) {
				mapVo.put("brif", "");
			}

			if (mapVo.get("sup_id") == null) {
				mapVo.put("sup_id", "");
			}

			if (mapVo.get("sup_no") == null) {
				mapVo.put("sup_no", "");
			}

			if (mapVo.get("dept_id") == null) {
				mapVo.put("dept_id", "");
			}
			
			if (mapVo.get("dept_no") == null) {
				mapVo.put("dept_no", "");
			}

			if (mapVo.get("stocker") == null) {
				mapVo.put("stocker", "");
			}
			
			if (mapVo.get("pur_hos_id") == null) {
				mapVo.put("pur_hos_id", mapVo.get("hos_id"));
			}
			
			if (mapVo.get("take_hos_id") == null) {
				mapVo.put("take_hos_id", "");
			}
			
			if (mapVo.get("pay_hos_id") == null) {
				mapVo.put("pay_hos_id", "");
			}
			
			if (mapVo.get("stock_type_code") == null) {
				mapVo.put("stock_type_code", "");
			}
			
			if (mapVo.get("pay_code") == null) {
				mapVo.put("pay_code", "");
			}
			
			if (mapVo.get("arr_address") == null) {
				mapVo.put("arr_address", "");
			}
		
			if (mapVo.get("maker") == null) {
				mapVo.put("maker", mapVo.get("user_id"));
			}

			if (mapVo.get("make_date") == null) {
				mapVo.put("make_date", "");
			}

		
			if (mapVo.get("checker") == null) {
				mapVo.put("checker", "");
			}

			if (mapVo.get("check_date") == null) {
				mapVo.put("check_date", "");
			}

			if (mapVo.get("state") == null) {
				mapVo.put("state", "1");
			}

			if (mapVo.get("is_notice") == null) {
				mapVo.put("is_notice", "0");
			}

			if (mapVo.get("notice_date") == null) {
				mapVo.put("notice_date", "");
			}

			if (mapVo.get("note") == null) {
				mapVo.put("note", "");
			}
			
			if (mapVo.get("come_from") == null) {
				mapVo.put("come_from", 1);
			}
			
			return mapVo;
		}
			
		// 返回用用于保存的默认值 明细
		public Map<String, Object> defaultDetailValue() {
			Map<String, Object> detailMap = new HashMap<String, Object>();
			
			detailMap.put("group_id", 0);
			detailMap.put("hos_id", 0);
			detailMap.put("copy_code", "");
			detailMap.put("amount", "0");
			detailMap.put("price", "0");
			detailMap.put("pack_code", "");
			detailMap.put("num", "0");
			detailMap.put("num_exchange", "0");
			detailMap.put("arrive_date", "");
			detailMap.put("memo", "");
			
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
	 * 添加订单 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			try {
				
				entityMap = defaultValue(entityMap);
				
				entityMap.put("order_code", getNextOrder_code(entityMap));
				//获取最大的ID
				entityMap.put("order_id", matOrderMainMapper.queryMatOrderNextval());
				
				Double money_sum = 0.0;
				
				//System.out.println("******** detailData:"+(String)entityMap.get("detailData"));
				if(entityMap.get("detailData") != null){
					//保存明细数据
					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
					List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
							Map<String,Object> detailMap = defaultDetailValue();
							detailMap.put("group_id", entityMap.get("group_id"));
							detailMap.put("hos_id", entityMap.get("hos_id"));
							detailMap.put("copy_code", entityMap.get("copy_code"));
							detailMap.put("order_id", entityMap.get("order_id"));//订单IDORDER_CODE
							detailMap.put("order_code", entityMap.get("order_code"));
							detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
							detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
							detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料no
							detailMap.put("price",  jsonObj.get("price"));//单价
							detailMap.put("amount",  jsonObj.get("amount"));//数量
							detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
							money_sum += Double.parseDouble(jsonObj.getString("amount_money"));
							if (validateJSON(String.valueOf(jsonObj.get("num")))) {//包装数量
								detailMap.put("num", jsonObj.get("num"));
							}
							if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {//包装单位
								detailMap.put("pack_code", jsonObj.get("pack_code"));
							}
							if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {//包装换算量
								detailMap.put("num_exchange", jsonObj.get("num_exchange"));
							}
							/*if (validateJSON(String.valueOf(jsonObj.get("arrive_date")))) {//计划到货日期
								detailMap.put("arrive_date", DateUtil.stringToDate( jsonObj.get("arrive_date").toString(), "yyyy-MM-dd"));//计划到货日期
							}*/
							if (validateJSON(String.valueOf(jsonObj.get("memo")))) {//备注
								detailMap.put("memo", jsonObj.get("memo"));
							}
							
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pur_rela")))){
								for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("pur_rela").toString())){
									Map<String,Object> relaMap = new HashMap<String,Object>();
									relaMap.put("group_id", entityMap.get("group_id"));
									relaMap.put("hos_id", entityMap.get("hos_id"));
									relaMap.put("copy_code", entityMap.get("copy_code"));
									relaMap.put("order_id", detailMap.get("order_id"));
									relaMap.put("order_detail_id", detailMap.get("order_detail_id"));
									
									relaMap.put("pur_id", m.get("pur_id"));
									relaMap.put("pur_detail_id", m.get("pur_detail_id"));
									relaMap.put("pur_amount", m.get("pur_amount"));
									relaMap.put("order_amount", m.get("order_amount"));
									
									relaList.add(relaMap);
								}
							}
							
							detailList.add(detailMap);
						}	
					}
				/*	System.out.println("************* detailList:"+detailList.size());*/
					entityMap.put("amount_money", money_sum);
					//新增入库主表数据
					matOrderMainMapper.add(entityMap);
					//新增订单明细数据
					if(detailList.size() > 0 ){
						matOrderDetailMapper.addBatchDetail(detailList);
					}
					
					if(relaList.size()>0){
						matOrderInitMapper.addBatchPOReal(relaList);
					}
				}
				
				entityMap.put("state", "3");
				entityMap.put("flag", "0");
				//entityMap.put("whereSql"," and ot.pur_amount != pt.pur_amount ");
				entityMap.put("whereSql"," and nvl(rt.countnum,0) != nvl(rt.equalnum,0) ");
				
				updateMatPurState(entityMap);
				
				return "{\"state\":\"true\",\"update_para\":\""+
					entityMap.get("group_id").toString()+","+
					entityMap.get("hos_id").toString()+","+
					entityMap.get("copy_code").toString()+","+
					entityMap.get("order_id").toString()+","
					+"\"}";
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
	}
	/**
	 * @Description 
	 * 批量添加 订单 <BR> 
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

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatOrderMain\"}";
		}
	}
	
	/**
	 * @Description 
	 * 修改  订单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		String purNewIds = "";
		
		try {
			List<Map<String,Object>> purRelaList = new ArrayList<Map<String,Object>>();
			//修改入库主表数据
			if(entityMap.get("order_date") != null && !"".equals(entityMap.get("order_date"))){
				entityMap.put("order_date", DateUtil.stringToDate(entityMap.get("order_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("make_date") != null && !"".equals(entityMap.get("make_date"))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("arrive_date") != null && !"".equals(entityMap.get("arrive_date"))){
				entityMap.put("arrive_date", DateUtil.stringToDate(entityMap.get("arrive_date").toString(), "yyyy-MM-dd"));
			}
			
			Double money_sum = 0.0;
			
			//修改明细数据
			JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			StringBuffer detail_ids = new StringBuffer();
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					Map<String,Object> detailMap = defaultDetailValue();
					
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("order_id", entityMap.get("order_id"));//主表ID
					detailMap.put("order_code", entityMap.get("order_code"));//主表ID
					detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
					detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料no
					detailMap.put("price",  jsonObj.get("price"));//单价
					detailMap.put("amount",  jsonObj.get("amount"));//数量
					detailMap.put("amount_money", jsonObj.get("amount_money"));
					money_sum += Double.parseDouble(jsonObj.getString("amount_money"));
					
					detailMap.put("line_state",  0);//省平台状态
					
					if (validateJSON(String.valueOf(jsonObj.get("num")))) {//包装数量
						detailMap.put("num", jsonObj.get("num"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {//包装单位
						detailMap.put("pack_code", jsonObj.get("pack_code"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {//包装换算量
						detailMap.put("num_exchange", jsonObj.get("num_exchange"));
					}
				/*	if (validateJSON(String.valueOf(jsonObj.get("arrive_date")))) {//计划到货日期
						detailMap.put("arrive_date", DateUtil.stringToDate( jsonObj.get("arrive_date").toString(), "yyyy-MM-dd"));//计划到货日期
					}*/
					if (validateJSON(String.valueOf(jsonObj.get("memo")))) {//备注
						detailMap.put("memo", jsonObj.get("memo"));
					}
					
					//明细表ID
					if(jsonObj.get("order_detail_id") == null || "".equals(jsonObj.get("order_detail_id"))){
						detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
						addList.add(detailMap);
					}else{
						detail_ids.append(jsonObj.get("order_detail_id").toString()).append(",");
						detailMap.put("order_detail_id", jsonObj.get("order_detail_id"));
						detailList.add(detailMap);
					}
					
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("order_rela")))){
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.getString("order_rela"))){
							Map<String,Object> purRelaMap = new HashMap<String,Object>();
							purRelaMap.put("group_id", entityMap.get("group_id"));
							purRelaMap.put("hos_id", entityMap.get("hos_id"));
							purRelaMap.put("copy_code", entityMap.get("copy_code"));
							purRelaMap.put("order_id", entityMap.get("order_id"));
							purRelaMap.put("order_detail_id", detailMap.get("order_detail_id"));
							if(!purNewIds.contains(m.get("pur_id").toString())){
								purNewIds=purNewIds+m.get("pur_id").toString()+",";
							}
							purRelaMap.put("pur_id", m.get("pur_id"));
							purRelaMap.put("pur_detail_id", m.get("pur_detail_id"));
							purRelaMap.put("order_amount", Float.parseFloat(m.get("order_amount").toString()));
							purRelaMap.put("pur_amount", Float.parseFloat(m.get("pur_amount").toString()));
							purRelaList.add(purRelaMap);
						}
					}
					
				}
			}
			
			entityMap.put("amount_money", money_sum);
			//更新主表
			matOrderMainMapper.update(entityMap);
			
			if(detail_ids.length() > 0){
				Map<String,Object> deleteMap = new HashMap<String,Object>();
				deleteMap.put("group_id", entityMap.get("group_id"));
				deleteMap.put("hos_id", entityMap.get("hos_id"));
				deleteMap.put("copy_code", entityMap.get("copy_code"));
				deleteMap.put("order_id", entityMap.get("order_id"));//主表ID
				deleteMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1));//明细IDS
				//System.out.println("*********** detail_ids: "+ detail_ids);
				//首先删除明细表中前台删除的明细数据
				matOrderDetailMapper.deleteForUpdate(deleteMap);
			}
			
			//更新明细表中数据
			if(detailList.size() > 0){
				matOrderDetailMapper.updateBatch(detailList);
			}
			
			//插入明细表中
			if(addList.size()>0){
				matOrderDetailMapper.addBatchDetail(addList);
			}
			
			//获取删除的pur_id
			if(!"".equals(purNewIds) && purNewIds!=null){
				entityMap.put("purNewIds", purNewIds.substring(0, purNewIds.length()-1));
				String oldPurId = matOrderInitMapper.queryDeleteOldIds(entityMap);
				if(oldPurId!=null && !"".equals(oldPurId)){
					entityMap.put("oldPurId", oldPurId);
				}
			}
			entityMap.put("order_ids", entityMap.get("order_id"));
			//重新建立入库单订单关系表
			if(purRelaList.size() > 0){
				matOrderInitMapper.deletePurOrder(entityMap);
				matOrderInitMapper.addBatchPOReal(purRelaList);
			}
			
			entityMap.put("state", "3");
			entityMap.put("flag", "1");
			//mapVo.put("whereSql"," and pt.pur_amount != ot.pur_amount ");
			entityMap.put("whereSql"," and nvl(rt.countnum,0) != nvl(rt.equalnum,0) ");
			
			updateMatPurState(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * @Description 
	 * 批量修改  订单<BR> 
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
			throw new SysException(e.getMessage());
		}	
	}

	/**
	 * @Description 
	 * 删除  订单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
	}
	
	/**
	 * @Description 
	 * 批量删除  订单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			StringBuffer order_ids = new StringBuffer() ;
			Map<String,Object> mapVo = new HashMap<String,Object>();
			for(int a = 0; a < entityList.size();a++){
				if(a == 0){
					mapVo.put("group_id", entityList.get(0).get("group_id"));
					mapVo.put("hos_id", entityList.get(0).get("hos_id"));
					mapVo.put("copy_code", entityList.get(0).get("copy_code"));
				}
				order_ids.append(entityList.get(a).get("order_id").toString()).append(",");
			}
			
			if(order_ids.length() > 0){
				mapVo.put("order_ids", order_ids.substring(0,order_ids.length()-1));
			}
			
			//更新采购计划状态
			String purIds = matOrderInitMapper.queryMatPurId(mapVo);
			if( purIds!=null && !"".equals(purIds)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("state", "2");
				map.put("purIds", purIds);
				matOrderInitMapper.updatePurStates(map);
			}
			
			//1、状态不为新建不能删除，从页面判断
			//2-1、按采购计划生成的采购订单：先还原采购计划单的状态到已审核（STATE=2）；
			//matOrderInitMapper.updateBatchPurOrder(mapVo);
			//2-2、删除对应关系表：采购订单关系表 MAT_PUR_ORDER_RELA；
			matOrderInitMapper.deletePurOrder(mapVo);
			//2-3、删除订单
			//3-1、合并采购订单生成的订单：先还原医院采购订单的状态到未审核（STATE=1）；
			matOrderInitMapper.updateBatchMergeOrder(mapVo);
			//3-2、删除对应关系表，订单合并关系表 MAT_ORDER_MERGE；
			matOrderInitMapper.deleteMergeOrder(mapVo);
			//3-3、删除订单
			
			//4、手工录入的订单，直接删除  2-3、3-3、一起
			//删除明细表
			matOrderDetailMapper.deleteBatch(entityList);
			//删除主表
			matOrderMainMapper.deleteBatch(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * @Description 
	 * 订单编制 -- 中止订单
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
    @Override
	public String abortMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException {

		try {	
			StringBuilder sb=new StringBuilder();
			
			for (Map<String, Object> map : entityList) {
				
				Map<String, Object> order = matOrderMainMapper.queryByCode(map);
				
				if( "1".equals(order.get("state").toString()) && "0".equals(order.get("state").toString()) && "5".equals(order.get("state").toString())){
					sb.append(order.get("order_code")).append(" ");
				}
		    }
			
			if(null != sb && !"".equals(sb.toString().trim())){
				return "{\"msg\":\""+sb.toString()+"单据不能终止.\",\"state\":\"true\"}";
			}
			
			matOrderInitMapper.abortMatOrderInit(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @Description 
	 * 查询结果集 代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
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
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matOrderMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matOrderMainMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
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
		return matOrderMainMapper.queryByCode(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 代销使用生成主查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiOut(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = matOrderInitMapper.queryAffiOut(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 代销使用生成主  查询代销出库主表
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryAffiOutMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return matOrderInitMapper.queryAffiOutMain(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 代销使用生成主  查询代销出库明细表
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matOrderInitMapper.queryAffiOutDetail(entityMap);
		return ChdJson.toJson(list);
		
	}
	/**
	 * 根据代销使用生成订单明细
	 */
	@Override
	public String genByAffiMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException {
		try{
			Map<String,Object> mapVo = new HashMap<String,Object>();
			StringBuffer out_ids = new StringBuffer() ;
			Double money_sum = 0.0;
			for(int a = 0 ; a < entityList.size() ; a++){
				if(a == 0){
					mapVo.put("group_id", entityList.get(0).get("group_id"));
					mapVo.put("hos_id", entityList.get(0).get("hos_id"));
					mapVo.put("copy_code", entityList.get(0).get("copy_code"));
					mapVo.put("user_id", entityList.get(0).get("user_id"));
					mapVo.put("make_date", entityList.get(0).get("make_date"));
					mapVo.put("year", entityList.get(0).get("year"));
					mapVo.put("month", entityList.get(0).get("month"));
					mapVo.put("is_dir", entityList.get(0).get("is_dir"));
					mapVo.put("table_code", "MAT_ORDER_MAIN");
					mapVo.put("prefixe", "DD");
				}
				out_ids.append(entityList.get(a).get("out_id").toString()).append(",");
			}
			
			if(out_ids.length() > 0){
				mapVo.put("out_ids", out_ids.substring(0,out_ids.length()-1));
			}
			
			//首先判断当前月在序号表中是否存在
			int flag = matNoManageMapper.queryIsExists(mapVo);
			if(flag == 0){//不存在，从1开始
				mapVo.put("max_no", 1);
				matNoManageMapper.add(mapVo);
			}
			
			List<Map<String,Object>> outDetailList = matOrderInitMapper.queryMatAffiOutDetailByGen(mapVo);
			Map<String, Object> mainMap = new HashMap<String,Object>();//主表
			
			//更新单据号表
			matNoManageMapper.updateMaxNo(mapVo); 
			
			String max_no = matNoManageMapper.queryMaxCode(mapVo);
			for (int a = max_no.length() ; a < 5; a++) {
				max_no = "0" + max_no;
			}
			String order_code = "DD" + mapVo.get("year") + mapVo.get("month") + max_no;
				
			mainMap.put("group_id", mapVo.get("group_id"));
			mainMap.put("hos_id", mapVo.get("hos_id"));
			mainMap.put("copy_code", mapVo.get("copy_code"));
			mainMap.put("order_id", matOrderMainMapper.queryMatOrderNextval());
			mainMap.put("order_code", order_code); //订单编号
			String order_date = mapVo.get("make_date").toString();
			mainMap.put("order_date", DateUtil.stringToDate(order_date, "yyyy-MM-dd")); //订单日期
				
			mainMap.put("pur_type", "1");//采购方式  自购  
			mainMap.put("order_type", "2");//单据类型 代销备货
				
			mainMap.put("pur_hos_id", mapVo.get("hos_id"));
			mainMap.put("pay_hos_id", mapVo.get("hos_id"));
			mainMap.put("take_hos_id", mapVo.get("hos_id"));
				
			mainMap.put("maker", mapVo.get("user_id"));
			mainMap.put("make_date", mapVo.get("make_date"));
			mainMap.put("is_dir", "0");
			
			List<Map<String,Object>> detailList  = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> map : outDetailList){
				Map<String, Object> detailMap = new HashMap<String,Object>();//明细表
					detailMap.put("group_id",map.get("group_id"));
					detailMap.put("hos_id",map.get("hos_id"));
					detailMap.put("copy_code",map.get("copy_code"));
					detailMap.put("order_id",mainMap.get("order_id"));
					detailMap.put("order_code",mainMap.get("order_code"));
					detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
					detailMap.put("inv_id",map.get("inv_id"));
					detailMap.put("inv_no",map.get("inv_no"));
					detailMap.put("price",map.get("price"));
					detailMap.put("amount",map.get("amount"));
					detailMap.put("amount_money", map.get("amount_money"));
					money_sum += Double.parseDouble(map.get("amount_money").toString());
					detailMap.put("num_exchange",map.get("num_exchange"));
					if (validateJSON(String.valueOf(map.get("num_exchange")))) {//包装数量
						detailMap.put("num",Double.parseDouble(map.get("amount").toString())/Double.parseDouble(map.get("num_exchange").toString()));
					}else{
						detailMap.put("num","0");
					}
					detailMap.put("pack_code",map.get("pack_code"));
					detailList.add(detailMap);
			}
			//System.out.println("***********mainList:"+ChdJson.toJson(detailList));
			mainMap.put("amount_money", money_sum);
			if(detailList.size()>0){
				matOrderMainMapper.add(mainMap);
				matOrderDetailMapper.addBatchDetail(detailList);
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"操作失败  明细为空.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
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
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matOrderDetailMapper.query(entityMap);
		return ChdJson.toJson(list);
		
	}
	
	
	/**
	 * 查订单明细  含对应关系
	 */
	@Override
	public String queryOrderDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matOrderInitMapper.queryOrderDetail(entityMap));
		return ChdJson.toJson(list);
	}
	
	
	/**
	 * @Description 
	 * 采购计划生成页面--查询采购计划信息
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatOrderGenPur(Map<String, Object> entityMap) throws DataAccessException {
	
		List<?> list = matOrderInitMapper.queryMatOrderGenPur(entityMap);
		return ChdJson.toJson(list);
	
	}
	/**
	 * @Description 
	 * 采购计划生成页面--根据主键查采购主表信息
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryMatPurById(Map<String, Object> entityMap) throws DataAccessException {
	
		return  matOrderInitMapper.queryMatPurById(entityMap);
	
	}
	
	/**
	 * @Description 
	 * 采购计划生成页面--根据主键查询采购明细信息
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPurDetail(Map<String, Object> entityMap) throws DataAccessException {
	
		List<?> list = matOrderInitMapper.queryMatPurDetail(entityMap);
		return ChdJson.toJson(list);
	
	}
	
	/**
	 * @Description 
	 * 采购计划生成页面--生成订单
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String genByPurMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			StringBuffer pur_ids = new StringBuffer() ;
			Map<String,Object> mapVo = new HashMap<String,Object>();
			for(int a = 0; a < entityList.size();a++){
				if(a == 0){
					mapVo.put("group_id", entityList.get(0).get("group_id"));
					mapVo.put("hos_id", entityList.get(0).get("hos_id"));
					mapVo.put("copy_code", entityList.get(0).get("copy_code"));
					mapVo.put("user_id", entityList.get(0).get("user_id"));
					mapVo.put("make_date", entityList.get(0).get("make_date"));
					mapVo.put("year", entityList.get(0).get("year"));
					mapVo.put("month", entityList.get(0).get("month"));
					mapVo.put("is_dir", entityList.get(0).get("is_dir"));
					mapVo.put("table_code", "MAT_ORDER_MAIN");
					mapVo.put("prefixe", "DD");
					
				}			
				pur_ids.append(entityList.get(a).get("pur_id").toString()).append(",");		
			}
			
			if(pur_ids.length() > 0){
				mapVo.put("pur_ids", pur_ids.substring(0,pur_ids.length()-1));
			}
			
			//System.out.println("************* pur_ids:"+mapVo.get("pur_ids"));
			//首先判断当前月在序号表中是否存在
			int flag = matNoManageMapper.queryIsExists(mapVo);
			if(flag == 0){//不存在，从1开始
				mapVo.put("max_no", 1);
				matNoManageMapper.add(mapVo);
			}
			
			//根据供应商、采购单位、请购单位、付款单位、材料是否代销汇总明细材料 
			List<Map<String, Object>> sumList = (List<Map<String, Object>>) matOrderInitMapper.queryMatOrderDetailList(mapVo);
			
			Map<String, Object> mainMap;//主表
			Map<String, Object> detailMap;//明细表
			
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();//主表集合
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();//明细集合
			
			Map<String, Object> ridMainMap = new HashMap<String,Object>();//用于去重主表信息
			Map<String, Object> ridDetailMap = new HashMap<String,Object>();//用于去重从表信息
			
			Map<String, Object> purOrder ;//对应关系对象
			List<Map<String, Object>> purOrderList = new ArrayList<Map<String,Object>>();//对应关系集合
			
		
			//循环sumList  区分出代销材料和非代销材料
			Long order_id = null;
			int order_type = 0;//订单类型：1、普通订单  2、代销备货订单
			String order_code = null;
			Double money_sum = 0.0;
			
			for(int i = 0; i < sumList.size() ; i++){
				mainMap = new HashMap<String,Object>();
				detailMap = new HashMap<String,Object>();
				purOrder =  new HashMap<String,Object>();//对应关系对象
				//System.out.println("*************:"+sumList.get(i).get("is_com").toString());
				//1、判断代销材料与否
				if(sumList.get(i).get("is_com").toString().equals("0")){
					order_type = 1;  //普通订单
				}
				if(sumList.get(i).get("is_com").toString().equals("1")){
					order_type = 2;  //代销备货订单
				}
				
				//2、拼接字符串，用于判断主表map去重
				String remarkM = sumList.get(i).get("group_id").toString()+sumList.get(i).get("hos_id").toString()+sumList.get(i).get("copy_code").toString();
				
				if(sumList.get(i).containsKey("pur_type")){
					remarkM += sumList.get(i).get("pur_type").toString();
				}
				if(sumList.get(i).containsKey("pur_hos_id")){
					remarkM += sumList.get(i).get("pur_hos_id").toString();
				}
				if(sumList.get(i).containsKey("req_hos_id")){
					remarkM += sumList.get(i).get("req_hos_id").toString();
				}
				if(sumList.get(i).containsKey("sup_no")){
					remarkM += String.valueOf(sumList.get(i).get("sup_no"))+String.valueOf(sumList.get(i).get("sup_id"));
				}
				remarkM += ";";
				
				//3、判断是否存在于去重map中，不存在，将字符串放进去
				if(ridMainMap.get(remarkM) == null){
					ridMainMap.put(remarkM, remarkM);
					//更新单据号表
					matNoManageMapper.updateMaxNo(mapVo);
					//存在,获取最大的单据号
					String max_no = matNoManageMapper.queryMaxCode(mapVo);
					for (int a = max_no.length() ; a < 5; a++) {
						max_no = "0" + max_no;
					}
					order_code = "DD" + mapVo.get("year") + mapVo.get("month") + max_no;
					
						
					mainMap.put("group_id", sumList.get(0).get("group_id"));
					mainMap.put("hos_id", sumList.get(0).get("hos_id"));
					mainMap.put("copy_code", sumList.get(0).get("copy_code"));
						
					order_id = matOrderMainMapper.queryMatOrderNextval();
					mainMap.put("order_id", order_id);
					mainMap.put("order_code", order_code); //订单编号
					mainMap.put("order_date", mapVo.get("make_date")); //订单日期
						
					mainMap.put("pur_type", sumList.get(i).get("pur_type"));//采购方式
					mainMap.put("order_type", order_type);//单据类型
					mainMap.put("sup_id", sumList.get(i).get("sup_id"));
					mainMap.put("sup_no", sumList.get(i).get("sup_no"));
						
					mainMap.put("pur_hos_id", sumList.get(i).get("pur_hos_id"));
					mainMap.put("pay_hos_id", sumList.get(i).get("pay_hos_id"));
					mainMap.put("take_hos_id", sumList.get(i).get("req_hos_id"));
						
					mainMap.put("maker", mapVo.get("user_id"));
					mainMap.put("make_date", mapVo.get("make_date"));
					mainMap.put("is_dir", mapVo.get("is_dir"));
					mainMap.put("come_from", 2);
					
					//加入主表list
					mainList.add(mainMap);
					
					String remarkD=sumList.get(i).get("group_id").toString()+sumList.get(i).get("hos_id").toString()+sumList.get(i).get("copy_code").toString()
							+sumList.get(i).get("inv_id").toString()+sumList.get(i).get("inv_no").toString()+order_id;
					if(ridDetailMap.get(remarkD) == null){
						ridDetailMap.put(remarkD, remarkD);
						//从表信息
						detailMap.put("group_id", sumList.get(i).get("group_id"));
						detailMap.put("hos_id", sumList.get(i).get("hos_id"));
						detailMap.put("copy_code", sumList.get(i).get("copy_code"));
						detailMap.put("order_id", order_id);
						detailMap.put("order_code", order_code);
						detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
						detailMap.put("inv_id", sumList.get(i).get("inv_id"));
						detailMap.put("inv_no", sumList.get(i).get("inv_no"));
						detailMap.put("amount", sumList.get(i).get("sum_amount"));
						detailMap.put("price", sumList.get(i).get("price"));
						detailMap.put("amount_money", sumList.get(i).get("amount_money"));
						
						//加入明细list
						detailList.add(detailMap);
					}
					
					//构造对应关系对象里数据
					purOrder.put("group_id", sumList.get(i).get("group_id"));
					purOrder.put("hos_id", sumList.get(i).get("hos_id"));
					purOrder.put("copy_code", sumList.get(i).get("copy_code"));
					purOrder.put("order_id", order_id);
					purOrder.put("pur_id", sumList.get(i).get("pur_id"));
					purOrder.put("inv_id", sumList.get(i).get("inv_id"));
					purOrder.put("pur_amount", sumList.get(i).get("rela_amount"));
					purOrder.put("order_amount", sumList.get(i).get("rela_req"));
					//加入到对应关系集合
					purOrderList.add(purOrder);
				}else{
					//若存在,只增加从表List
					String remarkD=sumList.get(i).get("group_id").toString()+sumList.get(i).get("hos_id").toString()+sumList.get(i).get("copy_code").toString()
							+sumList.get(i).get("inv_id").toString()+sumList.get(i).get("inv_no").toString()+order_id;
					if(ridDetailMap.get(remarkD) == null){
						ridDetailMap.put(remarkD, remarkD);
						//从表信息
						detailMap.put("group_id", sumList.get(i).get("group_id"));
						detailMap.put("hos_id", sumList.get(i).get("hos_id"));
						detailMap.put("copy_code", sumList.get(i).get("copy_code"));
						detailMap.put("order_id", order_id);
						detailMap.put("order_code", order_code);
						detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
						detailMap.put("inv_id", sumList.get(i).get("inv_id"));
						detailMap.put("inv_no", sumList.get(i).get("inv_no"));
						detailMap.put("amount", sumList.get(i).get("sum_amount"));
						detailMap.put("price", sumList.get(i).get("price"));
						
						//加入明细list
						detailList.add(detailMap);
					}
					
					//构造对应关系对象里数据
					purOrder.put("group_id", sumList.get(i).get("group_id"));
					purOrder.put("hos_id", sumList.get(i).get("hos_id"));
					purOrder.put("copy_code", sumList.get(i).get("copy_code"));
					purOrder.put("order_id", order_id);
					purOrder.put("pur_id", sumList.get(i).get("pur_id"));
					purOrder.put("inv_id", sumList.get(i).get("inv_id"));
					purOrder.put("pur_amount", sumList.get(i).get("rela_amount"));
					purOrder.put("order_amount", sumList.get(i).get("rela_req"));
					//加入到对应关系集合
					purOrderList.add(purOrder);
				}
			}
			//批量插入到订单主表
			if(mainList.size()>0){
				matOrderMainMapper.addBatchMain(mainList);
			}
			//批量插入到订单明细表
			if(detailList.size() > 0){
				matOrderDetailMapper.addBatchDetail(detailList);
				//批量更新订单主表金额
				matOrderMainMapper.updateMainMoneyForAddBatch(mapVo, mainList);
			}
			
			//批量插入到对应对应关系表
			if(purOrderList.size() > 0){
				matOrderInitMapper.addBatchPOReal(purOrderList);
			}
			
			//更新采购订单的状态为3
			matOrderInitMapper.updatePurBatch(mapVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}
	
	/**
	 * @Description 
	 * 订单编制--合并订单
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
    @Override
	public String mergeMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException {
		
    	List<Map<String, Object>> newentityList=new ArrayList<Map<String,Object>>();
		
		StringBuilder sb=new StringBuilder();
		
		for (Map<String, Object> map : entityList) {
			
			Map<String, Object> order = matOrderMainMapper.queryByCode(map);
			
			if(order.get("state").toString().equals("1")){
				newentityList.add(map);
			}else{
				
				if(order.get("is_notice").toString().equals("1") && !order.get("state").toString().equals("1")){
					
					sb.append(order.get("order_code")).append(" ");
				}
			}
        }

		if(null!=sb && !"".equals(sb.toString().trim())){
			return "{\"msg\":\""+sb.toString()+"单据不是新建状态不能合并.\",\"state\":\"true\"}";
		}
		
		try {
			StringBuffer order_ids = new StringBuffer() ;
			Map<String,Object> mapVo = new HashMap<String,Object>();
			Double money_sum = 0.0;
			for(int a = 0; a < newentityList.size();a++){
				if(a == 0){
					mapVo.put("group_id", entityList.get(0).get("group_id"));
					mapVo.put("hos_id", entityList.get(0).get("hos_id"));
					mapVo.put("copy_code", entityList.get(0).get("copy_code"));
					mapVo.put("user_id", entityList.get(0).get("user_id"));
					mapVo.put("make_date", entityList.get(0).get("make_date"));
					mapVo.put("year", entityList.get(0).get("year"));
					mapVo.put("month", entityList.get(0).get("month"));
					mapVo.put("table_code", "MAT_ORDER_MAIN");
					mapVo.put("prefixe", "DD");
					
				}
				
				order_ids.append(entityList.get(a).get("order_id").toString()).append(",");
				
			}
			if(order_ids.length() > 0){
				mapVo.put("order_ids", order_ids.substring(0,order_ids.length()-1));
			}
			
			List<Map<String, Object>> sumList = new ArrayList<Map<String,Object>>();//根据订单查询主表集合
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();//主表集合
			List<Map<String, Object>> megerList = new ArrayList<Map<String,Object>>();//对应关系集合
			Map<String, Object> megerMap = null ; //对应关系对象
			Map<String, Object> mainMap =null;//主表map
		
			
			//根据订单ID查询主表数据
			sumList = matOrderMainMapper.queryMatOrderMainCounts(mapVo);
			
			//如果供应商，采购单位，收货单位，付款单位信息不完全相同，则提示“不能合并”
			if(sumList.size() == 1){
				//组装主表数据，从表数据
				//查看最大的单据号
				mapVo.put("order_code", getNextOrder_code(mapVo));
				
				mainMap = new HashMap<String,Object>();
				
				mainMap.put("group_id", sumList.get(0).get("group_id"));
				mainMap.put("hos_id", sumList.get(0).get("hos_id"));
				mainMap.put("copy_code", sumList.get(0).get("copy_code"));
					
				mainMap.put("order_id", matOrderMainMapper.queryMatOrderNextval());
				
				mainMap.put("order_code", mapVo.get("order_code")); //订单编号
				mainMap.put("order_date", mapVo.get("make_date").toString());
				
					
				mainMap.put("pur_type", sumList.get(0).get("pur_type"));//采购方式
				mainMap.put("order_type", sumList.get(0).get("order_type"));//订单类型 --普通订单
				mainMap.put("is_dir", sumList.get(0).get("is_dir"));//是否定向  否
				
				if(sumList.get(0).get("is_dir")!=null  && "1".equals(sumList.get(0).get("is_dir").toString())){
					mainMap.put("dir_dept_id", sumList.get(0).get("dir_dept_id"));//科室id
					mainMap.put("dir_dept_no", sumList.get(0).get("dir_dept_no"));//科室NO
				}
				
				mainMap.put("sup_id", sumList.get(0).get("sup_id"));
				mainMap.put("sup_no", sumList.get(0).get("sup_no"));
				
				mainMap.put("store_id", sumList.get(0).get("store_id"));
				mainMap.put("store_no", sumList.get(0).get("store_no"));
					
				mainMap.put("pur_hos_id", sumList.get(0).get("pur_hos_id"));
				mainMap.put("pay_hos_id", sumList.get(0).get("pay_hos_id"));
				mainMap.put("take_hos_id", sumList.get(0).get("take_hos_id"));
					
				mainMap.put("maker", mapVo.get("user_id"));
				
				//计算自动到货日期---------------begin------------
				mainMap.put("para_code", "04007");
				int flag = 0;
				flag = Integer.parseInt(matCommonService.getMatAccPara(mainMap));
				String arrive_date = "";
				if(flag == 1){
					mainMap.put("para_code", "04008");
					//获取系统参数040008的值
					int num = Integer.parseInt(matCommonService.getMatAccPara(mainMap));
					
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal=Calendar.getInstance();
					Date date=cal.getTime();
					//获取当前系统日期
					//System.out.println(df.format(date));
					cal.add(Calendar.DATE, num);
					date=cal.getTime();
					arrive_date = df.format(date);
					
				}
				if (validateJSON(String.valueOf(arrive_date))) {//计划到货日期
					mainMap.put("arrive_date", arrive_date);//计划到货日期
				}
				
				//计算自动到货日期---------------end------------
				//System.out.println("*********** arrive_date:"+mapVo.get("arrive_date"));
				
				//组装从表数据
				mapVo.put("order_new_id", mainMap.get("order_id"));
				//mapVo.put("order_new_code", mainMap.get("order_code"));
				List<Map<String,Object>> detailList = matOrderDetailMapper.queryByOrderIds(mapVo);
				List<Map<String,Object>> detailNewList = new ArrayList<Map<String,Object>>(); 
				//合并时插入明细表数据
				money_sum = 0.0;
				if(detailList.size() > 0){
					for(int a = 0; a < detailList.size();a++){
						Map<String,Object> detailMap  = new HashMap<String,Object>();
						detailMap = (Map<String, Object>) detailList.get(a);
						detailMap.put("order_code", mainMap.get("order_code"));
						detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
						money_sum += Double.parseDouble(detailMap.get("amount_money").toString());
						detailNewList.add(detailMap);
					}
				}
				mainMap.put("amount_money", money_sum);
				mainList.add(mainMap);//加入主表集合
				
				//System.out.println(ChdJson.toJson(detailNewList));
				//组装合并表数据，插入到合并表
				for(int a = 0; a < entityList.size();a++){
					megerMap = new HashMap<String,Object>();
					megerMap.put("group_id", entityList.get(a).get("group_id"));
					megerMap.put("hos_id", entityList.get(a).get("hos_id"));
					megerMap.put("copy_code", entityList.get(a).get("copy_code"));
					megerMap.put("order_id_old", entityList.get(a).get("order_id"));
					megerMap.put("order_id_merge", mainMap.get("order_id"));
					
					megerList.add(megerMap);
				}
				
				//插入主表数据
				if(mainList.size() > 0){
					matOrderMainMapper.addBatchMain(mainList);
				}
				if(detailList.size() > 0){
					matOrderDetailMapper.addBatchDetailMerge(detailNewList);
				}
				//执行，插入到合并表
				matOrderInitMapper.addMergeOrder(megerList);
				//更新原订单订单的状态为 state=8
				matOrderMainMapper.updateOldMatOrderState(mapVo);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"供应商，需求库房，采购单位，收货单位，付款单位信息，/采购方式,订单类型，是否定向， 定向科室 信息不一致，不能合并！\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}
	
	/**
	 * 获取订单单号
	 * @param entityMap
	 * map参数必须包含(group_id, hos_id, copy_code, store_id, year, month, bus_type_code)这六个键值
	 * @return
	 */
	public String getNextOrder_code(Map<String, Object> entityMap){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		//map.put("store_id", entityMap.get("store_id"));
		//获取仓库别名store_alias
		//String store_alias =  matCommonMapper.queryStoreAliasById(map);
		map.put("table_code", "MAT_ORDER_MAIN");
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", "DD");
		map.put("store_alias", "");
		map.put("bus_type", "");
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
		for (int i = max_no.length() ; i < 5; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String req_code = "";
		req_code = "DD" + entityMap.get("year") + entityMap.get("month") + max_no;
		
		return req_code;
	}
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatOrderInitByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				
				 //查询入库主表
				List<Map<String, Object>> map = matOrderInitMapper.queryMatOrderInitPrintTemlateByMainBatch(entityMap);
					
			    //查询入库明细表
				List<Map<String,Object>> list=matOrderInitMapper.queryMatOrderInitPrintTemlateByDetail(entityMap);
				 
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			
			}else{
				 //查询入库主表
				Map<String,Object> map=matOrderInitMapper.queryMatOrderInitPrintTemlateByMain(entityMap);
					
					//查询入库明细表
				List<Map<String,Object>> list=matOrderInitMapper.queryMatOrderInitPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
			  
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		} 
		
	}
    
	@Override
	public Map<String,Object> queryMatOrderInitByPrintTemlatePage(Map<String, Object> map) throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatOrderInitMapper matOrderInitMapper = (MatOrderInitMapper)context.getBean("matOrderInitMapper");
			
			//查询入库主表
			if("1".equals(String.valueOf(map.get("p_num")))){ 
				
				 //查询入库主表
				List<Map<String, Object>> mainList = matOrderInitMapper.queryMatOrderInitPrintTemlateByMainBatch(map);
					
			    //查询入库明细表
				List<Map<String,Object>> detailList=matOrderInitMapper.queryMatOrderInitPrintTemlateByDetail(map);
				 
				//return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
				
				return reMap;
			
			}else{
				 //查询入库主表
				Map<String,Object> mainList=matOrderInitMapper.queryMatOrderInitPrintTemlateByMain(map);
					
					//查询入库明细表
				List<Map<String,Object>> detailList=matOrderInitMapper.queryMatOrderInitPrintTemlateByDetail(map);
				
				//return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
				
				return reMap;
			}
			  
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		} 
		
	}
	
	
	
	
	/**
	 * 采购计划导入主查询
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPurMainForOrder(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matOrderInitMapper.queryMatPurMainForOrder(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matOrderInitMapper.queryMatPurMainForOrder(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 采购计划导入明细查询
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPurDetailForOrder(Map<String, Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matOrderInitMapper.queryMatPurDetailForOrder(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 关闭采购明细
	 * @param entityList
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String updateMatPurDetailByOrderClose(List<Map<String, Object>> entityList) throws DataAccessException{
		try {	
			//批量关闭材料
			matOrderInitMapper.updateMatPurDetailByOrderClose(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCheckBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 根据采购计划生成订单
	 * @param entityList
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String addMatOrderByPurImp(Map<String, Object> entityMap) throws DataAccessException{
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
			
			//存放主表信息
			List<Map<String, Object>> orderMainList = new ArrayList<Map<String,Object>>();
			//存放明细信息
			List<Map<String, Object>> orderDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> purRelaList= new ArrayList<Map<String,Object>>();
			
			//记录主表信息
			Map<String, Map<String, Object>> orderMain = new HashMap<String, Map<String, Object>>();
			String orderMainKey = "";  
			//记录明细表信息
			Map<String, Map<String, Object>> orderDetail = new HashMap<String, Map<String, Object>>();
			String orderDetailKey = "";
			
			String order_id;
			String order_code;
			
			Map<String, String> purIds = new HashMap<String, String>();
			Map<String, Object> updatePur = new HashMap<String, Object>();
			updatePur.put("group_id", group_id);
			updatePur.put("hos_id", hos_id);
			updatePur.put("copy_code", copy_code);
			String pur_ids = "";
			
			entityMap.put("user_id", user_id);  //用于取出制单人所对应的职工ID
			String errorMsg = "";
			
			//查询所选主从表数据
			List<Map<String, Object>> orderList = JsonListMapUtil.toListMapLower(matOrderInitMapper.queryOrderByPurImp(entityMap));
			
			for(Map<String, Object> map : orderList){
				//判断材料是否含供应商
				if(map.get("sup_id") == null || "".equals(map.get("sup_id"))){
					errorMsg += "采购计划[" + map.get("pur_code") + "]中材料[" + map.get("inv_code").toString() + " " + map.get("inv_name").toString() + "]供应商为空！<br/>";
					continue;
				}
				
				/**
				 * 是否为省平台材料,
				 * 这个是按供应商来分单子,同一个供应商在一个单子里,在看同一个供应商里面是否包含省平台材料对应关系
				 * 1:这里规定 是省平台,0:不是省平台(下面if判断用)
				 */
				int matInv = matOrderInitMapper.queryMatInvRela(map);
				
				//用于更新采购计划
				if(!purIds.containsKey(map.get("pur_id"))){
					purIds.put(map.get("pur_id").toString(), "1");
				}
				//用于保存的主表数据
				Map<String, Object> mainMap;
				
				if(matInv > 0){
					
					orderMainKey = map.get("dept_id").toString() + map.get("sup_id").toString() 
							+ (map.get("store_id") == null ? "" : map.get("store_id").toString())
							+ map.get("pur_hos_id").toString() + map.get("take_hos_id").toString() 
							+ map.get("pay_hos_id").toString()+"1";
				}else{
					
					orderMainKey = map.get("dept_id").toString() + map.get("sup_id").toString() 
							+ (map.get("store_id") == null ? "" : map.get("store_id").toString())
							+ map.get("pur_hos_id").toString() + map.get("take_hos_id").toString() 
							+ map.get("pay_hos_id").toString()+"0";
				}
				orderDetailKey = orderMainKey + map.get("inv_id").toString();
				
				if(orderMain.containsKey(orderMainKey)){
					mainMap = orderMain.get(orderMainKey);
					order_id = mainMap.get("order_id").toString();
					order_code = mainMap.get("order_code").toString();
				}else{
					mainMap = new HashMap<String, Object>();
					mainMap.put("group_id", group_id);
					mainMap.put("hos_id", hos_id);
					mainMap.put("copy_code", copy_code);
					mainMap.put("year", year);
					mainMap.put("month", month);
					mainMap.put("pur_type", 1);	//采购方式
					mainMap.put("order_type", 1);	//单据类型
					
					//当是省平台材料对应关系表里的
					if(matInv > 0){
						mainMap.put("is_good", 1);
					}else{
						mainMap.put("is_good", 0);
					}
					mainMap.put("order_date", DateUtil.dateToString(make_date, "yyyy-MM-dd"));
					mainMap.put("make_date", make_date);
					mainMap.put("maker", user_id);
					
					mainMap.put("sup_id", map.get("sup_id"));
					mainMap.put("sup_no", map.get("sup_no"));
					mainMap.put("store_id", map.get("store_id"));
					mainMap.put("store_no", map.get("store_no"));
					mainMap.put("dept_id", map.get("dept_id"));	//采购科室ID
					mainMap.put("dept_no", map.get("dept_no"));	//采购科室变更号

					mainMap.put("stocker", map.get("stocker"));	//采购员
					mainMap.put("pur_hos_id", map.get("pur_hos_id"));	//采购单位
					mainMap.put("take_hos_id", map.get("take_hos_id"));	//收货单位ID
					mainMap.put("pay_hos_id", map.get("pay_hos_id"));	//付款单位ID
					mainMap.put("stock_type_code", null);	//采购类型
					mainMap.put("arr_address", entityMap.get("arr_address"));	//到货地址
					mainMap.put("arrive_date", DateUtil.dateToString(make_date, "yyyy-MM-dd"));	//计划到货日期
					mainMap.put("is_dir", map.get("is_dir"));	//是否定向
					mainMap.put("dir_dept_id", map.get("dir_dept_id"));	//定向科室ID
					mainMap.put("dir_dept_no", map.get("dir_dept_no"));	//定向科室变更ID
					mainMap.put("is_notice", null);	//通知状态
					mainMap.put("notice_date", null);	//通知日期
					
					mainMap.put("brif", "由采购计划生成");
					mainMap.put("note", null);	//备注
					mainMap.put("state", 1);
					mainMap.put("come_from", 2);	//数据来源
					//获取订单号
					order_code = getNextOrder_code(mainMap);
					mainMap.put("order_code", order_code);
					//获得自增ID
					order_id = String.valueOf(matOrderMainMapper.queryMatOrderNextval());
					mainMap.put("order_id", order_id);
					
					//存放到orderMain中
					orderMain.put(orderMainKey, mainMap);
				}
				Map<String, Object> detailMap;
				if(orderDetail.containsKey(orderDetailKey)){
					detailMap = orderDetail.get(orderDetailKey);
					detailMap.put("amount", ""+(Double.valueOf(detailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
					detailMap.put("amount_money", ""+(Double.valueOf(detailMap.get("amount_money").toString())+Double.valueOf(map.get("amount_money").toString())));
				}else{
					detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", group_id);
					detailMap.put("hos_id", hos_id);
					detailMap.put("copy_code", copy_code);
					
					detailMap.put("order_id", order_id);  //计划单ID
					detailMap.put("order_code", order_code);  //计划单号
					detailMap.put("order_detail_id", String.valueOf(matOrderDetailMapper.queryMatOrderDetailNextval()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //材料ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //材料变更ID
					
					detailMap.put("amount", map.get("amount").toString());  //数量
					detailMap.put("price", map.get("price").toString());  //计划单价
					detailMap.put("amount_money", map.get("amount_money").toString());  //金额
					
					detailMap.put("pack_code", null);  //包装单位
					detailMap.put("num", null);  //包装数量
					detailMap.put("num_exchange", null);  //包装换算量
					
					detailMap.put("memo", map.get("note") == null ? null : map.get("note").toString());  //备注
					detailMap.put("is_closed", "0");  //是否关闭
					detailMap.put("store_id", mainMap.get("store_id"));
					detailMap.put("store_no", mainMap.get("store_no"));
				}
				//存放到orderDetail中
				orderDetail.put(orderDetailKey, detailMap);
	
				/*******************处理对应关系*********begin**********/
				Map<String, Object> relaMap = new HashMap<String, Object>();
				relaMap.put("group_id", group_id);
				relaMap.put("hos_id", hos_id);
				relaMap.put("copy_code", copy_code);
				relaMap.put("order_id", order_id);
				relaMap.put("order_detail_id", detailMap.get("order_detail_id").toString());
				relaMap.put("pur_id", map.get("pur_id"));
				relaMap.put("pur_detail_id", map.get("pur_detail_id"));
				relaMap.put("pur_amount", map.get("amount").toString());
				relaMap.put("order_amount", map.get("amount").toString());
				purRelaList.add(relaMap);
				/*******************处理对应关系*********end************/
			}
			
			if(!"".equals(errorMsg)){
				return "{\"error\":\""+errorMsg+"\"}";
			}
			
			//解析主表
			for (String key : orderMain.keySet()) {
				orderMainList.add(orderMain.get(key));
			}
			
			//解析明细表
			for (String key : orderDetail.keySet()) {
				orderDetailList.add(orderDetail.get(key));
			}
			
			//解析采购计划ID
			for(String key : purIds.keySet()){
				pur_ids += key + ",";
			}
			updatePur.put("pur_ids", pur_ids.substring(0, pur_ids.length()-1));
			
			if(orderDetailList.size() > 0){
				//插入主表
				matOrderMainMapper.addBatchMain(orderMainList);
				//插入明细表
				matOrderDetailMapper.addBatchDetail(orderDetailList);
				//更新主表金额
				matOrderMainMapper.updateMainMoneyForAddBatch(entityMap, orderMainList);
				//添加对应关系
				matOrderInitMapper.addBatchPOReal(purRelaList);
				//更新采购订单的状态为3
				//matOrderInitMapper.updatePurBatch(updatePur);
			}else{
				return "{\"error\":\"操作失败，明细数据不能为空！\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 查询采购单明细
	 */
	@Override
	public String queryMatPurDetailGenOrder(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> list = matOrderInitMapper.queryMatPurDetailGenOrder(entityMap);
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String addMatOrderByPurDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/**-------------常量-------begin---------*/
			Date make_date = new Date();
			String[] dates =  DateUtil.dateToString(make_date, "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			String day = dates[2];
			/**-------------常量-------end-----------*/
			
			/**-------------  主表数据    begin---------*/
			Map<String,Object> mainMap = new HashMap<String, Object>();
			mainMap.put("group_id", entityMap.get("group_id").toString());
			mainMap.put("hos_id", entityMap.get("hos_id").toString());
			mainMap.put("copy_code", entityMap.get("copy_code").toString());
			mainMap.put("year", year);
			mainMap.put("month", month);
			mainMap.put("pur_type", entityMap.get("pur_type"));	//采购方式
			mainMap.put("stock_type_code", entityMap.get("stock_type_code"));	//单据类型
			mainMap.put("order_type", entityMap.get("order_type"));	//单据类型
			
			mainMap.put("order_date", DateUtil.stringToDate(entityMap.get("order_date").toString(), "yyyy-MM-dd"));
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			mainMap.put("make_date", df1.format(make_date));
			mainMap.put("maker", SessionManager.getUserId());
			
			mainMap.put("sup_id", entityMap.get("sup_id"));
			mainMap.put("sup_no", entityMap.get("sup_no"));
			mainMap.put("dept_id", entityMap.get("dept_id"));	//采购科室ID
			mainMap.put("dept_no", entityMap.get("dept_no"));	//采购科室变更号

			mainMap.put("stocker", entityMap.get("stocker"));	//采购员
			mainMap.put("pur_hos_id", entityMap.get("pur_hos_id"));	//采购单位
			mainMap.put("take_hos_id", entityMap.get("take_hos_id"));	//收货单位ID
			mainMap.put("pay_hos_id", entityMap.get("pay_hos_id"));	//付款单位ID
			mainMap.put("arr_address", "");	//到货地址
			if(entityMap.containsKey("store_id")){
				mainMap.put("store_id", entityMap.get("store_id"));
			}
			if(entityMap.containsKey("store_no")){
				mainMap.put("store_no", entityMap.get("store_no"));
			}
			mainMap.put("arr_address", "");	//到货地址
			
			
			//计算自动到货日期---------------begin------------
			mainMap.put("para_code", "04007");
			int flag = 0;
			flag = Integer.parseInt(matCommonService.getMatAccPara(mainMap));
			String arrive_date = "";
			if(flag == 1){
				mainMap.put("para_code", "04008");
				//获取系统参数040008的值
				int num = Integer.parseInt(matCommonService.getMatAccPara(mainMap));
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal=Calendar.getInstance();
				Date date=cal.getTime();
				//获取当前系统日期
				//System.out.println(df.format(date));
				cal.add(Calendar.DATE, num);
				date=cal.getTime();
				arrive_date = df.format(date);
				
			}
			if (validateJSON(String.valueOf(arrive_date))) {//计划到货日期
				mainMap.put("arrive_date", arrive_date);//计划到货日期
			}
			//计算自动到货日期---------------end------------
			
			mainMap.put("is_dir", entityMap.get("is_dir"));	//是否定向
			mainMap.put("dir_dept_id", entityMap.get("dir_dept_id"));	//定向科室ID
			mainMap.put("dir_dept_no", entityMap.get("dir_dept_no"));	//定向科室变更ID
			mainMap.put("is_notice", null);	//通知状态
			mainMap.put("notice_date", null);	//通知日期
			
			mainMap.put("brif", "由采购计划生成");
			mainMap.put("note", null);	//备注
			mainMap.put("state", 1);
			mainMap.put("come_from", entityMap.get("come_from"));	//数据来源
			//获取订单号
			mainMap.put("order_code", getNextOrder_code(mainMap));
			//获得自增ID
			mainMap.put("order_id", matOrderMainMapper.queryMatOrderNextval());
			entityMap.put("order_id", mainMap.get("order_id"));
			/**-------------  主表数据    end---------*/
			
			/**-------------  汇总明细数据    begin---------*/
			List<Map<String, Object>> detailLastList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> relaList = new ArrayList<Map<String,Object>>();
			Double money_sum = 0.0;
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("group_id", mainMap.get("group_id"));
						map.put("hos_id", mainMap.get("hos_id"));
						map.put("copy_code", mainMap.get("copy_code"));
						map.put("inv_id", jsonObj.get("inv_id"));
						map.put("inv_no", jsonObj.get("inv_no"));
					/*	if(ChdJson.validateJSON(String.valueOf(jsonObj.get("store_id")))){*/
							map.put("store_id", jsonObj.get("store_id"));
						/*}*/
				/*		if(ChdJson.validateJSON(String.valueOf(jsonObj.get("store_no")))){*/
							map.put("store_no", jsonObj.get("store_no"));
					/*	}*/  
						map.put("pur_id", jsonObj.get("pur_id"));
						map.put("pur_detail_id", jsonObj.get("pur_detail_id")); 
						map.put("pur_amount", jsonObj.get("pur_amount"));
						map.put("price", jsonObj.get("price"));
						map.put("amount", jsonObj.get("amount"));
						
						map.put("pack_code", jsonObj.get("pack_code"));
						map.put("num", jsonObj.get("num"));
						map.put("num_exchange", jsonObj.get("num_exchange"));
						
						
						
						detailList.add(map);
					}
				}
			}
			//汇总
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matOrderInitMapper.queryMatPurDetailCollect(detailList));
			
			/**-------------  汇总明细数据    end---------*/
			for(Map<String, Object> map : list){
				Map<String,Object> detailMap = new HashMap<String,Object>();
				detailMap.putAll(map);
				money_sum += Double.parseDouble(map.get("amount_money").toString());
				detailMap.put("order_id", mainMap.get("order_id"));
				detailMap.put("order_code", mainMap.get("order_code"));
				detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());
				detailMap.put("is_closed", "0");
				if(entityMap.containsKey("store_id")){
					detailMap.put("store_id", entityMap.get("store_id"));
				}
				if(entityMap.containsKey("store_no")){
					detailMap.put("store_no", entityMap.get("store_no"));
				}
				
				
				if(ChdJson.validateJSON(String.valueOf(map.get("pur_rela")))){
					for(Map<String, Object> m : JsonListMapUtil.getListMap(map.get("pur_rela").toString())){
						Map<String,Object> relaMap = new HashMap<String,Object>();
						relaMap.put("group_id", entityMap.get("group_id"));
						relaMap.put("hos_id", entityMap.get("hos_id"));
						relaMap.put("copy_code", entityMap.get("copy_code"));
						relaMap.put("order_id", detailMap.get("order_id"));
						relaMap.put("order_detail_id", detailMap.get("order_detail_id"));
						
						relaMap.put("pur_id", m.get("pur_id"));
						relaMap.put("pur_detail_id", m.get("pur_detail_id"));
						relaMap.put("pur_amount", m.get("pur_amount"));
						relaMap.put("order_amount", m.get("order_amount"));
						relaList.add(relaMap);
					}
				}
				detailLastList.add(detailMap);
			}
			
			
			if(detailLastList.size() > 0){
				//插入主表
				mainMap.put("amount_money", money_sum);
				matOrderMainMapper.add(mainMap);
				//插入明细表
				matOrderDetailMapper.addBatchDetail(detailLastList);
				if(relaList.size() > 0){
					//添加对应关系
					matOrderInitMapper.addBatchPOReal(relaList);
				}
			}else{
				return "{\"error\":\"操作失败，明细数据不能为空！\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
    /**
     * 更新采购单状态
     */
	@Override
	public void updateMatPurState(Map<String, Object> entityMap) throws DataAccessException {
		//若有删除的order_id  直接更新
		if(entityMap.containsKey("oldPurId")){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("state", "2");
			map.put("purIds", entityMap.get("oldPurId"));
			matOrderInitMapper.updatePurStates(map);
			entityMap.remove("order_id");
		}
			
		String purIds  = matOrderInitMapper.queryPurIds(entityMap);
		if( purIds!=null && !"".equals(purIds)){
			//更新状态  state = 3
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
				
			if(entityMap.get("state").equals("2")){
				map.put("state", "3");
			}
			if(entityMap.get("state").equals("3")){
				map.put("state", "2");
			}
				
			map.put("purIds", purIds);
			matOrderInitMapper.updatePurStates(map);
		}
	}
	
	@Override
	public String addMatOrderInitByPur(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/**-------------常量-------begin---------*/
			Date make_date = new Date();
			String[] dates =  DateUtil.dateToString(make_date, "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			String day = dates[2];
			/**-------------常量-------end-----------*/
			
			if(entityMap.get("make_date") != null && !"".equals(entityMap.get("make_date"))){
				entityMap.put("make_date", DateUtil.dateToString((Date) entityMap.get("make_date"), "yyyy-MM-dd"));
			}
			entityMap.put("order_date", DateUtil.stringToDate(entityMap.get("order_date").toString(), "yyyy-MM-dd"));
			/**-------------  主表数据    begin---------*/
			entityMap.put("group_id", entityMap.get("group_id").toString());
			entityMap.put("hos_id", entityMap.get("hos_id").toString());
			entityMap.put("copy_code", entityMap.get("copy_code").toString());
			entityMap.put("year", year);
			entityMap.put("month", month);
			entityMap.put("maker", SessionManager.getUserId());
		
			entityMap.put("is_notice", null);	//通知状态
			entityMap.put("notice_date", null);	//通知日期
			
			entityMap.put("brif", "由采购计划生成");
			entityMap.put("note", entityMap.get("note"));	//备注
			entityMap.put("state", 1);
			entityMap.put("come_from", entityMap.get("come_from"));	//数据来源
			//获取订单号
			entityMap.put("order_code", getNextOrder_code(entityMap));
			//获得自增ID
			entityMap.put("order_id", matOrderMainMapper.queryMatOrderNextval());
			if("1".equals(entityMap.get("pur_type"))){
				entityMap.put("take_hos_id",entityMap.get("pur_hos_id"));
				entityMap.put("pay_hos_id", entityMap.get("pur_hos_id"));
			}
			
			/**-------------  主表数据    end---------*/
			/**-------------  明细数据  begin---------*/
			String purIds = "";
			Double money_sum = 0.0;
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("detailData") != null){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = defaultDetailValue();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("order_id", entityMap.get("order_id"));//订单IDORDER_CODE
						detailMap.put("order_code", entityMap.get("order_code"));
						detailMap.put("order_detail_id", matOrderDetailMapper.queryMatOrderDetailNextval());//明细ID
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料no
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money", Double.parseDouble(jsonObj.getString("price")) * Double.parseDouble(jsonObj.getString("amount")));
						money_sum = Double.parseDouble(jsonObj.getString("price")) * Double.parseDouble(jsonObj.getString("amount"));
						
						if (validateJSON(String.valueOf(jsonObj.get("num")))) {//包装数量
							detailMap.put("num", jsonObj.get("num"));
						}
						if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {//包装单位
							detailMap.put("pack_code", jsonObj.get("pack_code"));
						}
						if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {//包装换算量
							detailMap.put("num_exchange", jsonObj.get("num_exchange"));
						}
						if (validateJSON(String.valueOf(jsonObj.get("memo")))) {//备注
							detailMap.put("memo", jsonObj.get("memo"));
						}
						
						if(entityMap.containsKey("store_id")){
							detailMap.put("store_id", entityMap.get("store_id"));
						}
						if(entityMap.containsKey("store_no")){
							detailMap.put("store_no", entityMap.get("store_no"));
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pur_rela")))){
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("pur_rela").toString())){
								Map<String,Object> relaMap = new HashMap<String,Object>();
								relaMap.put("group_id", entityMap.get("group_id"));
								relaMap.put("hos_id", entityMap.get("hos_id"));
								relaMap.put("copy_code", entityMap.get("copy_code"));
								relaMap.put("order_id", detailMap.get("order_id"));
								relaMap.put("order_detail_id", detailMap.get("order_detail_id"));
								
								relaMap.put("pur_id", m.get("pur_id"));
								relaMap.put("pur_detail_id", m.get("pur_detail_id"));
								relaMap.put("pur_amount", m.get("pur_amount"));
								relaMap.put("order_amount", m.get("order_amount"));
								if(!purIds.contains(m.get("pur_id").toString())){
									purIds=purIds+m.get("pur_id").toString()+",";
								}
								relaList.add(relaMap);
							}
						}
						
						detailList.add(detailMap);
					}	
				}
			}
			
			if(detailList.size() > 0){
				//插入主表
				entityMap.put("amount_money", money_sum);
				matOrderMainMapper.add(entityMap);
				//插入明细表
				matOrderDetailMapper.addBatchDetail(detailList);
				
				//获取pur_id
				if(!"".equals(purIds) && purIds!=null){
					entityMap.put("purIds", purIds.substring(0, purIds.length()-1));
				}
				
				if(relaList.size() > 0){
					//添加对应关系
					matOrderInitMapper.addBatchPOReal(relaList);
				}
			}else{
				return "{\"error\":\"操作失败，明细数据不能为空！\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		
		return "{\"state\":\"true\",\"update_para\":\""+
		entityMap.get("group_id").toString()+","+
		entityMap.get("hos_id").toString()+","+
		entityMap.get("copy_code").toString()+","+
		entityMap.get("order_id").toString()+","
		+"\"}";
	}
	/**
	 * 订单编制 查询--明细查询
	 */
	@Override
	public String queryShowDetail(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matOrderMainMapper.queryShowDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matOrderMainMapper.queryShowDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String stateMatOrderInit(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			StringBuffer errMsg=new StringBuffer();//错误信息
			//调用订单明细接口
			ServerUrl su=new ServerUrl();
			
			logger.debug("++++++++++++++begin+++++++++++++++++++");
			String url = su.getProperty("chdsup.ip", "chdsup.H002");//参看 HRP系统的 serverUrl.properties属性文件
			logger.debug("++++++++++++++调用订单明细借口,url="+url);
			List<String> orderDetailIDList= zjMapper.getPlatformOrderDetailId(mapVo);//每个订单编码和明细编码
			logger.debug("++++++++++++++每个订单的编码和明细数据,orderDetailIDList="+orderDetailIDList);
			if(orderDetailIDList.size()<=0){
				return "{\"error\":\"没有订单单明细编号进行插入！\"}";
			}
			List<Map<String, String>> platformOrderDetailList=zjMapper.getPlatformOrderDetailsByHrpOrderId(mapVo);
			logger.debug("++++++++++++++省平台材料是否做对应关系,platformOrderDetailList="+platformOrderDetailList);
			logger.debug("++++++++++++++省平台材料是否做对应关系,循环开始");
			for (Map<String, String> map : platformOrderDetailList) {
				//移出没用字段,否则发送省平台报错
				map.remove("inv_code");
				map.remove("inv_name");
				map.remove("sup_code");
				map.remove("sup_name");
				if (StringUtils.isBlank(map.get("goodsID"))) {
					errMsg.append("物资材料:  ");
					errMsg.append(map.get("inv_code")).append(map.get("inv_name")).append(",");
				}
				
			}
			logger.debug("++++++++++++++省平台材料是否做对应关系,循环结束");
			if (StringUtils.isNotBlank(errMsg.toString())) {
				return "{\"state\":\"false\",\"errMsg\":\""+errMsg.toString().substring(0,errMsg.toString().length()-1)+"未做省平台对应关系,此订单无法同步\"}";
			}else{
				Map<String, String> getOrderMap=new HashMap<String, String>();
				getOrderMap.put("currentPageNumber", "1");//后期需要通过计算来获取页码,如计算省平台一共能传过来多少条数据,在看看咱们系统有多少明细数据,他们相除算出页码
				//还少一个采购部门,现在先不用填,不填默认第一个
				getOrderMap.put("list", JSON.toJSONString(orderDetailIDList));
				logger.debug("++++++++++++++封装好的数据开始发送"+getOrderMap);
				String getOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getOrderMap));
				logger.debug("++++++++++++++封装好的数据返回数据"+getOrderResultStr);
				Map orderReturnMap = JSONObject.parseObject(getOrderResultStr, Map.class);
				
				logger.debug("++++++++++++++省平台返回结果解析开始,状态编码"+orderReturnMap.get("returnCode")+"错误信息="+orderReturnMap.get("returnMsg"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				
				String orderDetail ;
				if ("1".equals(String.valueOf(orderReturnMap.get("returnCode")))) {
					List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(orderReturnMap.get("successList")), List.class);
					for (Map<String, Object> map : successList) {
						Map<String, Object> lineOrderMap=new HashMap<String, Object>();
						lineOrderMap.put("orderId", map.get("orderId").toString());
						lineOrderMap.put("orderDetailID", map.get("orderDetailID").toString());
						logger.debug("++++++++++++++根据返回的数据查询开始"+lineOrderMap);
						 orderDetail  = zjMapper.queryOrderDetail(lineOrderMap);
						 logger.debug("++++++++++++++根据返回的数据查询出订单编码和明细编码"+orderDetail);
						lineOrderMap.put("order_detail_id", orderDetail);
						lineOrderMap.put("order_id", mapVo.get("order_id"));
						lineOrderMap.put("orderDetailState", map.get("orderDetailState").toString());
						detailList.add(lineOrderMap);
					}
					logger.debug("++++++++++++++保存在HRP数据库开始"+detailList);
					 matOrderMainMapper.updateState(detailList);
					 logger.debug("++++++++++++++保存在HRP数据库结束"+matOrderMainMapper.updateState(detailList));
				} else {
					return "{\"state\":\"false\",\"errMsg\":\"订单省平台同步失败"+String.valueOf(orderReturnMap.get("returnCode"))+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
				}
				
				//---------------------------------------------------------------------------------
				
				logger.debug("++++++++++++++开启配送单信息++++++++++++++++++++++++++++++++++++++++++");
				List<Map<String, Object>> orderMapList = zjMapper.queryPlatformDelivery(mapVo);
				
				logger.debug("++++++++++++++获取需要的数据准备抽取需要数据="+orderMapList);
				List<String> orderDetailIDList1 = new ArrayList<String>();
				for(Map<String, Object> orderMap : orderMapList){
					String platform_detail_id  = orderMap.get("platform_detail_id").toString();
					String order_detail_id  = orderMap.get("order_detail_id").toString();
					orderDetailIDList1.add(platform_detail_id);
				}
				logger.debug("++++++++++++++获取配送单的数据准备发送给省平台="+orderDetailIDList1);
				//获取配送信息保存到关系表里
				logger.debug("++++++++++++++获取配送单的,开始发送省平台=");
				ServerUrl su1=new ServerUrl();
				String url1 = su1.getProperty("chdsup.ip", "chdsup.H004");//参看 HRP系统的 serverUrl.properties属性文件
				logger.debug("++++++++++++++获取配送单的,省平台路径="+url1);
				Map<String, String> getOrderMap1=new HashMap<String, String>();
				getOrderMap1.put("currentPageNumber", "1");//后期需要通过计算来获取页码,如计算省平台一共能传过来多少条数据,在看看咱们系统有多少明细数据,他们相除算出页码
				//还少一个采购部门,现在先不用填,不填默认第一个
				getOrderMap1.put("list", JSON.toJSONString(orderDetailIDList1));
				String getOrderResultStr1 = HttpClientTool.doPost(url1, JSON.toJSONString(getOrderMap1));
				logger.debug("++++++++++++++获取配送单的,省平台返回的数据="+getOrderResultStr1);
				Map orderReturnMap1 = JSONObject.parseObject(getOrderResultStr1, Map.class);
				if ("1".equals(String.valueOf(orderReturnMap1.get("returnCode")))) {
					List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(orderReturnMap1.get("successList")), List.class);
					
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> map : successList) {
						Map<String,Object> orderMap = new HashMap<String,Object>();
						//if("8".equals(map.get("orderDetailState"))){
							//获取配送订单
							//url = su.getProperty("chdsup.ip", "chdsup.H004");
							//Map<String, String> submitOrderMap=new HashMap<String, String>();
							//String submitOrderReturnStr = HttpClientTool.doPost(url, JSON.toJSONString(submitOrderMap));
							//Map submitOrderReturnMap = JSONObject.parseObject(submitOrderReturnStr, Map.class);
							//if ("1".equals(String.valueOf(submitOrderReturnMap.get("returnCode")))) {
								//List<Map<String, Object>> successList1 = JSONObject.parseObject(String.valueOf(submitOrderReturnMap.get("successList")), List.class);
								  
								  String orderDetailID = map.get("orderDetailID").toString();//订单明细编码
								  String distributionSerialID = map.get("distributionSerialID").toString();//配送明细编码
								  String invoiceID = map.get("invoiceID").toString();//发票号
								  String batchRecordID = map.get("batchRecordID").toString();//批号
								  String distributeCount = map.get("distributeCount").toString();//[配送数量
								  String distributeTime = map.get("distributeTime").toString();//[配送时间
								  String warehouseCount = map.get("warehouseCount").toString();//入库数量
								  String warehouseTime = map.get("warehouseTime").toString();//入库时间
								  
								  orderMap.put("orderDetailID", orderDetailID);
								  orderMap.put("distributionSerialID", distributionSerialID);
								  orderMap.put("invoiceID", invoiceID);
								  orderMap.put("batchRecordID", batchRecordID);
								  orderMap.put("distributeCount", distributeCount);
								  orderMap.put("distributeTime", distributeTime);
								  orderMap.put("warehouseCount", warehouseCount);
								  orderMap.put("warehouseTime", warehouseTime);
								  orderMap.put("group_id", SessionManager.getGroupId());
								  orderMap.put("hos_id",  SessionManager.getHosId());
								  orderMap.put("copy_code", SessionManager.getCopyCode());
								  list.add(orderMap);
							//}else{
								//return "{\"state\":\"false\",\"errMsg\":\"订单省平台提交失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
							//}
						//}else{
						//	return "{\"msg\":\"该订单,没有收到货物\",\"state\":\"false\"}";
						//}
					}
					logger.debug("++++++++++++++准备保存zj_order_map"+list);
					
					 List<Map<String, Object>> size = zjMapper.queryPlatformDeliveryDis(mapVo);
					if(size.size()> 0){
						zjMapper.deleteDistribution(list);
					}
						zjMapper.insertDistribution(list);
					
					
					logger.debug("++++++++++++++准备保存结束++++++++++++");
				}else{
					//note 值为 orderMain 说明主表添加失败,值为orderDetail 说明明细表添加失败
					if("orderMain".equals(String.valueOf(orderReturnMap1.get("note")))){
						return "{\"state\":\"false\",\"errMsg\":\"主表添加失败"+String.valueOf(orderReturnMap1.get("returnMsg"))+"\"}";
					}else{
						return "{\"state\":\"false\",\"errMsg\":\"明细表添加失败"+String.valueOf(orderReturnMap1.get("returnMsg"))+"\"}";
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"同步失败,数据库异常\"}";   
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	
		}
	}
