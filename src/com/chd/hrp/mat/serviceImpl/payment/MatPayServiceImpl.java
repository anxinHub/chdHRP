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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.payment.MatPayMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.payment.MatPayService;
import com.github.pagehelper.PageInfo;

/** 
 * 
 * @Description:
 * 付款单
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matPayService")
public class MatPayServiceImpl implements MatPayService {

	private static Logger logger = Logger.getLogger(MatPayServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPayMapper")
	private final MatPayMapper matPayMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	//主查询
	@Override
	public String queryMatPayList(Map<String, Object> map) throws DataAccessException{
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
		if(map.get("pay_date_begin") != null && !"".equals(map.get("pay_date_begin").toString())){
			map.put("pay_date_begin", DateUtil.stringToDate(map.get("pay_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(map.get("pay_date_end") != null && !"".equals(map.get("pay_date_end").toString())){
			map.put("pay_date_end", DateUtil.stringToDate(map.get("pay_date_end").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		List<Map<String, Object>> list = null;
		
		if (sysPage.getTotal()==-1){
			
			list = matPayMapper.queryMatPayList(map);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = matPayMapper.queryMatPayList(map, rowBounds);
			
			PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//加载单据
	@Override
	public Map<String, Object> queryMatPayMainById(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		return matPayMapper.queryMatPayMainById(map);
	}

	@Override
	public String queryMatPayDetailById(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		return matCommonService.getDetailJsonByDetailListPay(matPayMapper.queryMatPayDetailById(map));
	}
	
	//保存发票（新增和修改）
	@Override
	public Map<String, Object> saveMatPay(Map<String, Object> map) throws DataAccessException{
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
			
			if(map.get("pay_id") != null && !"".equals(map.get("pay_id").toString())){
				//校验发票状态，已审核不能修改
				map.put("exists_state", 2);
				int count = matPayMapper.existsMatPayState(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "发票已审核不能修改！");
					return retMap;
				}
			}

			//转换日期
			if(map.get("pay_date") != null && !"".equals(map.get("pay_date").toString())){
				map.put("pay_date", DateUtil.stringToDate(map.get("pay_date").toString(), "yyyy-MM-dd"));
			}else{
				retMap.put("state", "false");
				retMap.put("error", "发票日期为空！");
				return retMap;
			}
			
			boolean is_add = false;
			//如果是系统生成则是添加，需获取流水号和主键
			if(map.get("pay_bill_no") != null && "系统生成".equals(map.get("pay_bill_no").toString())){
				Map<String, Object> codeMap = new HashMap<String, Object>();
				String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
				codeMap.put("year", date.substring(0, 4));
				codeMap.put("month", date.substring(5, 7));
				codeMap.put("day", date.substring(8, 10));
				codeMap.put("table_code", "MAT_PAY_MAIN");
				codeMap.put("prefixe", "FK");
				
				map.put("pay_bill_no", matCommonService.getMatNextNo(codeMap));
				
				//获取主键
				Long pay_id = matPayMapper.queryPayMainSeq();
				map.put("pay_id", pay_id);
				
				is_add = true;
			}
			
			//解析得明细结果集（明细存在勾选则取勾选的明细，不勾选则取所有明细）
			JSONArray json = JSONArray.parseArray((String)map.get("allData"));
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			Map<String,Object> detailMap = null;
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("bill_id") == null || "".equals(jsonObj.getString("bill_id"))){
					//空行直接跳过
					continue;
				}
				//明细存在勾选则取勾选的明细，不勾选则取所有明细
				for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("detail_data").toString())){
					if(m.get("check_row") == null || !"1".equals(m.get("check_row").toString())){
						//明细没有勾选则不保存本行数据
						continue;
					}
					detailMap = new HashMap<String,Object>();
					if(m.get("pay_detail_id") != null && !"".equals(m.get("pay_detail_id").toString())){
						detailMap.put("pay_detail_id", m.get("pay_detail_id").toString());
					}else{
						detailMap.put("pay_detail_id", String.valueOf(matPayMapper.queryPayDetailSeq()));
					}
					detailMap.put("bill_id", m.get("bill_id").toString());
					detailMap.put("bill_detail_id", m.get("bill_detail_id").toString());
					detailMap.put("bill_money", m.get("bill_money").toString());
					detailMap.put("payed_money", m.get("payed_money").toString());
					detailMap.put("dis_money", m.get("dis_money").toString());
					detailMap.put("pay_money", m.get("pay_money").toString());
					
					detailList.add(detailMap);
				}
			}
			
			if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "解析后明细数据为空，请检验是否已勾选入库单明细！");
				return retMap;
			}
			
			if(is_add){
				//新增主表
				matPayMapper.addMatPayMain(map);
			}else{
				//修改主表
				matPayMapper.updateMatPayMainById(map);
				//删除明细
				matPayMapper.deleteMatPayDetailById(map);
			}
			//新增明细表
			matPayMapper.addMatPayDetail(map, detailList);
			
			retMap.put("state", "true");
			if(is_add){
				retMap.put("msg1", "添加成功！");
			}else{
				retMap.put("msg", "修改成功！");
			}
			retMap.put("pay_id", map.get("pay_id").toString());
			retMap.put("pay_bill_no", map.get("pay_bill_no").toString());
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//删除发票
	@Override
	public Map<String, Object> deleteMatPay(Map<String, Object> map) throws DataAccessException{
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
				tmpMap.put("pay_id", id);
				list.add(tmpMap);
			}

			//校验发票状态，已审核不能删除
			map.put("exists_state", 2);
			int count = matPayMapper.existsMatPayStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在已审核发票不能删除！");
				return retMap;
			}
			
			//判断是否已生成凭证
			String pay_bill_nos = matPayMapper.existsVouchByMatPay(map, list);
			if(pay_bill_nos != null && pay_bill_nos.length() > 0){
				retMap.put("state", "false");
				retMap.put("msg", "付款单"+pay_bill_nos+"已生成凭证，不能删除！");
				return retMap;
			}
			
			//删除发票
			matPayMapper.deleteMatPay(map, list);
			
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
	public Map<String, Object> updateMatPayState(Map<String, Object> map) throws DataAccessException{
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
				tmpMap.put("pay_id", id);
				list.add(tmpMap);
			}
			
			//校验发票状态
			int count = matPayMapper.existsMatPayStateBatch(map, list);
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
				String pay_bill_nos = matPayMapper.existsVouchByMatPay(map, list);
				if(pay_bill_nos != null && pay_bill_nos.length() > 0){
					retMap.put("state", "false");
					retMap.put("msg", "付款单"+pay_bill_nos+"已生成凭证，不能消审！");
					return retMap;
				}
			}
			
			//修改状态
			matPayMapper.updateMatPayState(map, list);
			//修改发票中的付款金额
			matPayMapper.updateMatBillMoneyByPay(map, list);
			
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
	public Map<String, Object> updateMatPayNote(Map<String, Object> map) throws DataAccessException{
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
				tmpMap.put("pay_id", id);
				list.add(tmpMap);
			}
			
			//修改状态
			matPayMapper.updateMatPayNote(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//查询发票
	@Override
	public String queryMatBillByPay(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		//发票日期
		if(map.get("bill_date_begin") != null && !"".equals(map.get("bill_date_begin").toString())){
			map.put("bill_date_begin", DateUtil.stringToDate(map.get("bill_date_begin").toString(), "yyyy-MM-dd"));
		} 
		if(map.get("bill_date_end") != null && !"".equals(map.get("bill_date_end").toString())){
			map.put("bill_date_end", DateUtil.stringToDate(map.get("bill_date_end").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		List<Map<String, Object>> list = null;
		
		if (sysPage.getTotal()==-1){
			
			list = matPayMapper.queryMatBillByPay(map);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = matPayMapper.queryMatBillByPay(map, rowBounds);
			
			PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatBillDetailByPay(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		return ChdJson.toJson(matPayMapper.queryMatBillDetailByPay(map));
	}
	
	//根据所选发票生成付款单明细
	@Override
	public String queryMatPayDetailByBill(Map<String, Object> map) throws DataAccessException{
		
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
				detailMap.put("bill_id", jsonObj.getString("bill_id"));
				detailMap.put("bill_code", jsonObj.getString("bill_code"));
				detailMap.put("bill_no", jsonObj.getString("bill_no"));
				detailMap.put("store_id", jsonObj.get("store_id"));//该字段用于页面判断，不可去掉
				detailMap.put("store_name", jsonObj.getString("store_name"));
				detailMap.put("sup_name", jsonObj.getString("sup_name"));
				detailMap.put("bill_date", jsonObj.getString("bill_date"));
				detailMap.put("chk_date", jsonObj.getString("chk_date"));
				detailMap.put("note", jsonObj.getString("note"));
				detailMap.put("maker_name", jsonObj.getString("maker_name"));
				detailMap.put("checker_name", jsonObj.getString("checker_name"));
				detailMap.put("bill_state_name", jsonObj.get("bill_state_name"));//中文名称用于显示
				for(Map<String, Object> tmpMap : JsonListMapUtil.getListMap(jsonObj.get("detail_data").toString())){
					tmpMap.putAll(detailMap);
					list.add(tmpMap);
				}
			}else{
				//不存在从后台查询
				detailMap.put("bill_id", jsonObj.get("bill_id"));
				idList.add(detailMap);
			}
		}
		
		if(idList.size() > 0){
			List<Map<String,Object>> detailList = matPayMapper.queryMatPayDetailByBill(map, idList);
			if(detailList != null && detailList.size() > 0){
				for(Map<String, Object> tmpMap : detailList){
					list.add(tmpMap);
				}
			}
		}
		
		return matCommonService.getDetailJsonByDetailListPay(list);
	}

	
	//模板打印
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	//模板打印（包含主从表）
	@Override
	public String queryMatPayByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException{
		try {
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matPayMapper.queryMatPayMainPrintByTemlateBatch(entityMap);
				List<Map<String,Object>> list=matPayMapper.queryMatPayDetailPrintByTemlate(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=matPayMapper.queryMatPayMainPrintByTemlate(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matPayMapper.queryMatPayDetailPrintByTemlate(entityMap);
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询付款单打印数据
	 * @param entityMap
	 * @return
	 */
	@Override
	public Map<String, Object> queryMatPayMainByPrintTemlateNew(Map<String, Object> entityMap){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatPayMapper matPayMapper = (MatPayMapper)context.getBean("matPayMapper");
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map =matPayMapper.queryMatPayMainPrintByTemlateBatch(entityMap);
				List<Map<String,Object>> list=matPayMapper.queryMatPayDetailPrintByTemlate(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}else{
				
				Map<String,Object> map=matPayMapper.queryMatPayMainPrintByTemlate(entityMap);
				
				//查询明细表
				List<Map<String,Object>> list=matPayMapper.queryMatPayDetailPrintByTemlate(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 查询付款单明细打印数据
	 * @param entityMap
	 * @return
	 */
	@Override
	public Map<String, Object> queryMatPayDetailByPrintTemlateNew(Map<String, Object> entityMap){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatPayMapper matPayMapper = (MatPayMapper)context.getBean("matPayMapper");

			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				//按批号打印
				return result;
			}else{
				//查询主表
                Map<String,Object> map=matPayMapper.queryMatPayDetailByPrintByMain(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matPayMapper.queryMatPayDetailByPrintByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	//表格打印
	@Override
	public List<Map<String, Object>> queryMatPayMainForPrint(Map<String, Object> entityMap) throws DataAccessException{
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
		if(entityMap.get("pay_date_begin") != null && !"".equals(entityMap.get("pay_date_begin").toString())){
			entityMap.put("pay_date_begin", DateUtil.stringToDate(entityMap.get("pay_date_begin").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("pay_date_end") != null && !"".equals(entityMap.get("pay_date_end").toString())){
			entityMap.put("pay_date_end", DateUtil.stringToDate(entityMap.get("pay_date_end").toString(), "yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> list = matPayMapper.queryMatPayList(entityMap);
		//转日期格式
		for(Map<String, Object> tmp : list){
			if(tmp.get("pay_date") != null && !"".equals(tmp.get("pay_date"))){
				tmp.put("pay_date", DateUtil.dateToString((Date) tmp.get("pay_date"), "yyyy-MM-dd"));
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
