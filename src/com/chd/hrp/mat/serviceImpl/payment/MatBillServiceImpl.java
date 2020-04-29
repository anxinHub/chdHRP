/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.payment;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.payment.MatBillMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.payment.MatBillService;
import com.github.pagehelper.PageInfo;

/** 
 * 
 * @Description:
 * 采购发票
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matBillService")
public class MatBillServiceImpl implements MatBillService {

	private static Logger logger = Logger.getLogger(MatBillServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matBillMapper")
	private final MatBillMapper matBillMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	//主查询
	@Override
	public String queryMatBillList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		//制单日期
		if(map.get("make_date_begin") != null && !"".equals(map.get("make_date_begin").toString())){
			map.put("make_date_begin", DateUtil.stringToDate(map.get("make_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(map.get("make_date_end") != null && !"".equals(map.get("make_date_end").toString())){
			map.put("make_date_end", DateUtil.stringToDate(map.get("make_date_end").toString(), "yyyy-MM-dd"));
		}
		//发票日期
		if(map.get("bill_date_begin") != null && !"".equals(map.get("bill_date_begin").toString())){
			map.put("bill_date_begin", DateUtil.stringToDate(map.get("bill_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(map.get("bill_date_end") != null && !"".equals(map.get("bill_date_end").toString())){
			map.put("bill_date_end", DateUtil.stringToDate(map.get("bill_date_end").toString(), "yyyy-MM-dd"));
		}
		//入库日期
		if(map.get("in_date_begin") != null && !"".equals(map.get("in_date_begin").toString())){
			map.put("in_date_begin", DateUtil.stringToDate(map.get("in_date_begin").toString(), "yyyy-MM-dd"));
		} 
		if(map.get("in_date_end") != null && !"".equals(map.get("in_date_end").toString())){
			map.put("in_date_end", DateUtil.stringToDate(map.get("in_date_end").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		List<Map<String, Object>> list = null;
		
		if (sysPage.getTotal()==-1){
			
			list = matBillMapper.queryMatBillList(map);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = matBillMapper.queryMatBillList(map, rowBounds);
			
			PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//加载单据
	@Override
	public Map<String, Object> queryMatBillMainById(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		return matBillMapper.queryMatBillMainById(map);
	}

	@Override
	public String queryMatBillDetailById(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
			map.put("table_main", "mat_nopay_deliver");
			map.put("id_col", "deliver_id");
			map.put("no_col", "deliver_no");
			map.put("table_detail", "mat_nopay_deliver_d");
			map.put("detail_id_col", "detail_id");
		}else{
			map.put("table_main", "mat_in_main");
			map.put("id_col", "in_id");
			map.put("no_col", "in_no");
			map.put("table_detail", "mat_in_detail");
			map.put("detail_id_col", "in_detail_id");
		}
		
		return matCommonService.getDetailJsonByDetailListBill(matBillMapper.queryMatBillDetailById(map));
	}
	
	//保存发票（新增和修改）
	@Override
	public Map<String, Object> saveMatBill(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			//添加常量
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("ori_no", "0000");
			map.put("stock_type_code", "");
			map.put("maker", SessionManager.getUserId());
			map.put("make_date", new Date());
			map.put("state", "1");
			
			if(map.get("bill_id") != null && !"".equals(map.get("bill_id").toString())){
				//校验发票状态，已审核不能修改
				map.put("exists_state", 2);
				int count = matBillMapper.existsMatBillState(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "发票已审核不能修改！");
					return retMap;
				}
			}

			//转换日期
			if(map.get("bill_date") != null && !"".equals(map.get("bill_date").toString())){
				map.put("bill_date", DateUtil.stringToDate(map.get("bill_date").toString(), "yyyy-MM-dd"));
			}else{
				retMap.put("state", "false");
				retMap.put("error", "发票日期为空！");
				return retMap;
			}
			//发票状态为空则默认为0
			if(map.get("bill_state") == null || "".equals(map.get("bill_state").toString())){
				map.put("bill_state", 0);
			}
			
			//判断发票号是否重复
			int count = matBillMapper.queryBillNoExists(map);  
			if (count > 0) {
				retMap.put("state", "false");
				retMap.put("error", "发票号重复！");
				return retMap;
			}
			
			boolean is_add = false;
			//如果是系统生成则是添加，需获取流水号和主键
			if(map.get("bill_code") != null && "系统生成".equals(map.get("bill_code").toString())){
				Map<String, Object> codeMap = new HashMap<String, Object>();
				String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
				codeMap.put("year", date.substring(0, 4));
				codeMap.put("month", date.substring(5, 7));
				codeMap.put("day", date.substring(8, 10));
				codeMap.put("table_code", "MAT_BILL_MAIN");
				codeMap.put("prefixe", "FP");
				
				map.put("bill_code", matCommonService.getMatNextNo(codeMap));
				
				//获取主键
				Long bill_id = matBillMapper.queryBillMainSeq();
				map.put("bill_id", bill_id);
				
				is_add = true;
			}
			
			//解析得明细结果集（明细存在勾选则取勾选的明细，不勾选则取所有明细）
			JSONArray json = JSONArray.parseArray((String)map.get("allData"));
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();
			Map<String, String> inIdMap = new HashMap<String, String>();
			Map<String,Object> detailMap = null;
			Map<String,Object> relaMap = null;
			Iterator<Object> it = json.iterator();
			boolean is_first = true;
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("in_id") == null || "".equals(jsonObj.getString("in_id"))){
					//空行直接跳过
					continue;
				}
				//明细存在勾选则取勾选的明细，不勾选则取所有明细
				for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("detail_data").toString())){
					if(m.get("check_row") == null || !"1".equals(m.get("check_row").toString())){
						//明细没有勾选则不保存本行数据
						continue;
					}
					//系统参数04079为1时需判断发票日期和入库日期同一个月份  状态为 货票同到
					if(is_first && "1".equals(MyConfig.getSysPara("04079"))){
						String in_date = jsonObj.getString("in_date");
						String bill_date = DateUtil.dateToString((Date) map.get("bill_date"), "yyyy-MM-dd");
						if(in_date.substring(0, 7).equals(bill_date.substring(0, 7))){
							map.put("bill_state", 1);
						}
						is_first = false;
					}
					detailMap = new HashMap<String,Object>();
					if(m.get("bill_detail_id") != null && !"".equals(m.get("bill_detail_id").toString())){
						detailMap.put("bill_detail_id", m.get("bill_detail_id").toString());
					}else{
						detailMap.put("bill_detail_id", String.valueOf(matBillMapper.queryBillDetailSeq()));
					}
					detailMap.put("in_id", m.get("in_id").toString());
					detailMap.put("in_detail_id", m.get("in_detail_id").toString());
					detailMap.put("payable_money", m.get("payable_money").toString());
					detailMap.put("bill_money", m.get("bill_money").toString());
					if(m.get("bill_no") != null && !"".equals(m.get("bill_no").toString())){
						detailMap.put("bill_no", m.get("bill_no").toString());
					}else{
						detailMap.put("bill_no", map.get("bill_no").toString());
					}
					
					//记录发票入库单，以便于更新入库单发票
					if(!inIdMap.containsKey(detailMap.get("in_id").toString())){
						relaMap = new HashMap<String,Object>();
						relaMap.put("in_id", detailMap.get("in_id").toString());
						
						relaList.add(relaMap);
						inIdMap.put(detailMap.get("in_id").toString(), "1");
					}
					
					detailList.add(detailMap);
				}
			}
			
			if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "解析后明细数据为空，请检验是否已勾选入库单明细！");
				return retMap;
			}
			
			//校验与采购发票的发票状态不一致的入库单是否已生成凭证
			String in_nos = matBillMapper.existsVouchByMatIn(map, relaList);
			if(in_nos != null && in_nos.length() > 0){
				retMap.put("state", "false");
				retMap.put("msg", "入库单"+in_nos+"已生成凭证，不能修改发票状态！");
				return retMap;
			}
			
			if(is_add){
				//新增主表
				matBillMapper.addMatBillMain(map);
			}else{
				//修改主表
				matBillMapper.updateMatBillMainById(map);
				//删除明细
				matBillMapper.deleteMatBillDetailById(map);
			}
			//新增明细表
			matBillMapper.addMatBillDetail(map, detailList);
			//更新入库单发票信息
			/**
			 * updateMatIn取系统参数"04078"
			 * 为0时不更改入库单原来的发票号（即发票号为空的才修改）(默认)
			 * 为1时修改入库单发票号与采购发票一致
			 */
			map.put("updateMatIn", MyConfig.getSysPara("04078"));
			boolean is_init = true;
			if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
				map.put("table_main", "mat_nopay_deliver");
				map.put("id_col", "deliver_id");
			}else{
				map.put("table_main", "mat_in_main");
				map.put("id_col", "in_id");
				is_init = false;
			}
			matBillMapper.updateMatInMainInvoiceBatch(map, relaList);
			if(!is_init){
				matBillMapper.updateMatSpecialMainInvoiceBatch(map, relaList);
			}
			
			retMap.put("state", "true");
			if(is_add){
				retMap.put("msg1", "添加成功！");
			}else{
				retMap.put("msg", "修改成功！");
			}
			retMap.put("bill_id", map.get("bill_id").toString());
			retMap.put("bill_no", map.get("bill_no").toString());
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//删除发票
	@Override
	public Map<String, Object> deleteMatBill(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("bill_id", id);
				list.add(tmpMap);
			}

			//校验发票状态，已审核不能删除
			map.put("exists_state", 2);
			int count = matBillMapper.existsMatBillStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在已审核发票不能删除！");
				return retMap;
			}
			
			//判断是否已生成凭证
			String bill_nos = matBillMapper.existsVouchByMatBill(map, list);
			if(bill_nos != null && bill_nos.length() > 0){
				retMap.put("state", "false");
				retMap.put("msg", "发票"+bill_nos+"已生成凭证，不能删除！");
				return retMap;
			}
			
			//发票删除时是否清空入库表中发票信息
			int yesOrno =  Integer.parseInt(MyConfig.getSysPara("04071").toString());
			if(yesOrno == 1){
				List<Map<String, Object>> inList = matBillMapper.queryMatInIdByBillId(map, list);
				if(inList.size() > 0){
					map.put("updateMatIn", MyConfig.getSysPara("04078"));  //这个参数等于1时才会修改发票号和发票日期
					map.put("bill_state", 0);
					map.put("bill_date", null);
					map.put("bill_no", "");
					
					if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
						map.put("table_main", "mat_nopay_deliver");
						map.put("id_col", "deliver_id");
					}else{
						map.put("table_main", "mat_in_main");
						map.put("id_col", "in_id");
					}
					matBillMapper.updateMatInMainInvoiceBatch(map, inList);
				}
			}
			
			//删除发票
			matBillMapper.deleteMatBill(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "删除成功");
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//修改审核状态
	@Override
	public Map<String, Object> updateMatBillState(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("bill_id", id);
				list.add(tmpMap);
			}
			
			//校验发票状态
			int count = matBillMapper.existsMatBillStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				if(map.get("state") != null && "1".equals(map.get("state").toString())){
					retMap.put("error", "存在未审核发票，不能消审！");
				}else{
					retMap.put("error", "存在已审核发票，不能审核！");
				}
				return retMap;
			}
			
			//消审时判断是否已生成凭证
			if(map.get("state") != null && "1".equals(map.get("state").toString())){
				String bill_nos = matBillMapper.existsVouchByMatBill(map, list);
				if(bill_nos != null && bill_nos.length() > 0){
					retMap.put("state", "false");
					retMap.put("msg", "发票"+bill_nos+"已生成凭证，不能消审！");
					return retMap;
				}
			}
			
			//修改状态
			matBillMapper.updateMatBillState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//修改单据备注
	@Override
	public Map<String, Object> updateMatBillNote(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("bill_id", id);
				list.add(tmpMap);
			}
			
			//修改状态
			matBillMapper.updateMatBillNote(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//查询入库单
	@Override
	public String queryMatInByBill(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		//入库日期
		if(map.get("in_date_begin") != null && !"".equals(map.get("in_date_begin").toString())){
			map.put("in_date_begin", DateUtil.stringToDate(map.get("in_date_begin").toString(), "yyyy-MM-dd"));
		} 
		if(map.get("in_date_end") != null && !"".equals(map.get("in_date_end").toString())){
			map.put("in_date_end", DateUtil.stringToDate(map.get("in_date_end").toString(), "yyyy-MM-dd"));
		}
		
		if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
			map.put("table_main", "mat_nopay_deliver");
			map.put("id_col", "deliver_id");
			map.put("no_col", "deliver_no");
		}else{
			map.put("table_main", "mat_in_main");
			map.put("id_col", "in_id");
			map.put("no_col", "in_no");
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		List<Map<String, Object>> list = null;
		
		if (sysPage.getTotal()==-1){
			
			list = matBillMapper.queryMatInByBill(map);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = matBillMapper.queryMatInByBill(map, rowBounds);
			
			PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatInDetailByBill(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
			map.put("table_main", "mat_nopay_deliver");
			map.put("id_col", "deliver_id");
			map.put("no_col", "deliver_no");
			map.put("table_detail", "mat_nopay_deliver_d");
			map.put("detail_id_col", "detail_id");
		}else{
			map.put("table_main", "mat_in_main");
			map.put("id_col", "in_id");
			map.put("no_col", "in_no");
			map.put("table_detail", "mat_in_detail");
			map.put("detail_id_col", "in_detail_id");
		}

		return ChdJson.toJson(matBillMapper.queryMatInDetailByBill(map));
	}
	
	//根据所选入库单生成采购发票明细
	@Override
	public String queryMatBillDetailByIn(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//解析得明细结果集（明细存在勾选则取勾选的明细，不勾选则取所有明细）
		List<Map<String,Object>> idList = new ArrayList<Map<String,Object>>();
		JSONArray json = JSONArray.parseArray((String)map.get("detailData"));
		Map<String,Object> detailMap = null;
		Iterator<Object> it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			detailMap = new HashMap<String, Object>();
			//明细存在则取明细
			if(jsonObj.get("detail_data") != null && !"".equals(jsonObj.getString("detail_data"))){
				detailMap.put("group_id", jsonObj.getString("group_id"));
				detailMap.put("hos_id", jsonObj.getString("hos_id"));
				detailMap.put("copy_code", jsonObj.getString("copy_code"));
				detailMap.put("in_no", jsonObj.getString("in_no"));
				detailMap.put("bus_type_name", jsonObj.getString("bus_type_name"));
				detailMap.put("store_id", jsonObj.get("store_id"));//该字段用于页面判断，不可去掉
				detailMap.put("store_name", jsonObj.getString("store_name"));
				detailMap.put("sup_name", jsonObj.getString("sup_name"));
				detailMap.put("in_date", jsonObj.getString("in_date"));
				detailMap.put("confirm_date", jsonObj.getString("confirm_date"));
				detailMap.put("brief", jsonObj.getString("brief"));
				detailMap.put("maker_name", jsonObj.getString("maker_name"));
				detailMap.put("confirmer_name", jsonObj.getString("confirmer_name"));
				detailMap.put("main_bill_no", jsonObj.get("main_bill_no"));
				detailMap.put("bill_state", jsonObj.get("bill_state"));
				detailMap.put("bill_state_name", jsonObj.get("bill_state_name"));//中文名称用于显示
				for(Map<String, Object> tmpMap : JsonListMapUtil.getListMap(jsonObj.get("detail_data").toString())){
					tmpMap.putAll(detailMap);
					list.add(tmpMap);
				}
			}else{
				//不存在从后台查询
				detailMap.put("in_id", jsonObj.get("in_id"));
				idList.add(detailMap);
			}
		}

		if(map.get("is_init") != null && "1".equals(map.get("is_init").toString())){
			map.put("table_main", "mat_nopay_deliver");
			map.put("id_col", "deliver_id");
			map.put("no_col", "deliver_no");
			map.put("table_detail", "mat_nopay_deliver_d");
			map.put("detail_id_col", "detail_id");
		}else{
			map.put("table_main", "mat_in_main");
			map.put("id_col", "in_id");
			map.put("no_col", "in_no");
			map.put("table_detail", "mat_in_detail");
			map.put("detail_id_col", "in_detail_id");
		}
		
		if(idList.size() > 0){
			List<Map<String,Object>> detailList = matBillMapper.queryMatBillDetailByIn(map, idList);
			if(detailList != null && detailList.size() > 0){
				for(Map<String, Object> tmpMap : detailList){
					list.add(tmpMap);
				}
			}
		}
		
		return matCommonService.getDetailJsonByDetailListBill(list);
	}
	
	//模板打印（包含主从表）
	@Override
	public String queryMatBillByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException{
		try {
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matBillMapper.queryMatBillMainPrintByTemlateBatch(entityMap);
				List<Map<String,Object>> list=matBillMapper.queryMatBillDetailPrintByTemlate(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=matBillMapper.queryMatBillMainPrintByTemlate(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matBillMapper.queryMatBillDetailPrintByTemlate(entityMap);
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	//查询 发票是否已付款
	@Override
	public int queryPayOrNot(Map<String, Object> map) throws DataAccessException{
		
		return matBillMapper.queryPayOrNot(map);
	}
	
	//模板打印
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> queryMatBillMainByPrintPage(Map<String, Object> entityMap)throws DataAccessException{
		try {
			Map<String,Object> reMap=new HashMap<String,Object>();
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map = matBillMapper.queryMatBillMainPrintByTemlateBatch(entityMap);
				reMap.put("main", map);
			}else{
				Map<String,Object> map=matBillMapper.queryMatBillMainPrintByTemlate(entityMap);
				reMap.put("main", map);
			}
			
			List<Map<String,Object>> list = matBillMapper.queryMatBillDetailPrintByTemlate(entityMap);
			reMap.put("detail", list);
			
			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	//表格打印
	@Override
	public List<Map<String, Object>> queryMatBillMainForPrint(Map<String, Object> entityMap) throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		//制单日期
		if(entityMap.get("make_date_begin") != null && !"".equals(entityMap.get("make_date_begin").toString())){
			entityMap.put("make_date_begin", DateUtil.stringToDate(entityMap.get("make_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("make_date_end") != null && !"".equals(entityMap.get("make_date_end").toString())){
			entityMap.put("make_date_end", DateUtil.stringToDate(entityMap.get("make_date_end").toString(), "yyyy-MM-dd"));
		}
		//发票日期
		if(entityMap.get("bill_date_begin") != null && !"".equals(entityMap.get("bill_date_begin").toString())){
			entityMap.put("bill_date_begin", DateUtil.stringToDate(entityMap.get("bill_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("bill_date_end") != null && !"".equals(entityMap.get("bill_date_end").toString())){
			entityMap.put("bill_date_end", DateUtil.stringToDate(entityMap.get("bill_date_end").toString(), "yyyy-MM-dd"));
		}
		//入库日期
		if(entityMap.get("in_date_begin") != null && !"".equals(entityMap.get("in_date_begin").toString())){
			entityMap.put("in_date_begin", DateUtil.stringToDate(entityMap.get("in_date_begin").toString(), "yyyy-MM-dd"));
		} 
		if(entityMap.get("in_date_end") != null && !"".equals(entityMap.get("in_date_end").toString())){
			entityMap.put("in_date_end", DateUtil.stringToDate(entityMap.get("in_date_end").toString(), "yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> list = matBillMapper.queryMatBillList(entityMap);
		//转日期格式
		for(Map<String, Object> tmp : list){
			if(tmp.get("bill_date") != null && !"".equals(tmp.get("bill_date"))){
				tmp.put("bill_date", DateUtil.dateToString((Date) tmp.get("bill_date"), "yyyy-MM-dd"));
			}
			if(tmp.get("make_date") != null && !"".equals(tmp.get("make_date"))){
				tmp.put("make_date", DateUtil.dateToString((Date) tmp.get("make_date"), "yyyy-MM-dd"));
			}
			if(tmp.get("chk_date") != null && !"".equals(tmp.get("chk_date"))){
				tmp.put("chk_date", DateUtil.dateToString((Date) tmp.get("chk_date"), "yyyy-MM-dd"));
			}
		}
		
		return list;
	}
}
