/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.account.report.MatAccountReportDeptOutMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountReportDeptOutService")
public class MatAccountReportDeptOutServiceImpl implements MatAccountReportDeptOutService {

	private static Logger logger = Logger.getLogger(MatAccountReportDeptOutServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountReportDeptOutMapper")
	private final MatAccountReportDeptOutMapper matAccountReportDeptOutMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountReportDeptOut(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountReportDeptOutMapper.queryMatAccountReportDeptOut(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMatInByType(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> dblist = JsonListMapUtil.toListMapLower(matAccountReportDeptOutMapper.queryMatInByType(entityMap));
		List<Map<String,Object>> list = new  ArrayList<Map<String,Object>>();//非重List
		List<Map<String,Object>> returnList = new  ArrayList<Map<String,Object>>();//返回数据List
		//过滤虚库,仓库重复数据
		Set<String> setStoreCodeSet=new HashSet<String>();
		for (Map<String, Object> map : dblist) {
			String setStoreCode=map.get("set_code")+"@"+map.get("store_code");
			if (!setStoreCodeSet.contains(setStoreCode)) {
				setStoreCodeSet.add(setStoreCode);
				list.add(map);
			}
		}
		for (Map<String, Object> map : list) {
			//装填一行数据基础信息
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("set_name", map.get("set_name"));
			returnMap.put("set_code", map.get("set_code"));
			returnMap.put("store_name", map.get("store_name"));
			returnMap.put("store_code", map.get("store_code"));
			//装填此行数据  各个 物资类别  对应的总金额  以及汇总金额
			String returnSetStoreCode=returnMap.get("set_code")+"@"+returnMap.get("store_code");
			double sumAccount=0;
			for (Map<String, Object> accountByTypeMap : dblist) {
				String accountSetStoreCode=accountByTypeMap.get("set_code")+"@"+accountByTypeMap.get("store_code");
				if (returnSetStoreCode!=null && returnSetStoreCode.equals(accountSetStoreCode)) {
					//获取查询数据的金额
					double dbSum=Double.parseDouble(accountByTypeMap.get("money").toString());
					//获取查询数据的类别编码的主类别(前两位)
					String type=accountByTypeMap.get("mat_type_code").toString().substring(0, 2);
					//判断行数据map中是否含有此主类别,有则增加对应金额,没有再则行数据map中添加此类别
					if (returnMap.get(type)!=null) {
						double preSum=Double.parseDouble(returnMap.get(type).toString());
						returnMap.put(type, preSum+dbSum);
					}else{
						returnMap.put(type, dbSum);
					}
					//增加汇总金额
					sumAccount+=dbSum;
				}
			}
			returnMap.put("amount_money", sumAccount);
			returnList.add(returnMap);
		}
		return ChdJson.toJsonLower(returnList);
//		List<Map<String,Object>> newlist = new  ArrayList<Map<String,Object>>();
//		List<Map<String,Object>> lastList = new  ArrayList<Map<String,Object>>();
//		for(Map<String,Object> map : list){
//			Map<String, Object> newMap = new HashMap<String,Object>();
//			newMap.put("set_name", map.get("set_name"));
//			newMap.put("set_code", map.get("set_code"));
//			newMap.put("store_name", map.get("store_name"));
//			newMap.put("store_code", map.get("store_code"));
//			newMap.put(map.get("mat_type_code").toString(), map.get("money"));
//			newMap.put(map.get("is_last").toString(), map.get("is_last"));
//			newlist.add(newMap);
//		}
//		
//		for(Map<String,Object> m : newlist){
//			StringBuffer set_storeCode = new StringBuffer();
//			set_storeCode.append(m.get("set_code").toString()).append(m.get("store_code").toString());
//			
//			Double amount_money = 0.00;
//			for(Map<String,Object> oldMap : list){
//				StringBuffer setStoreCode = new StringBuffer();
//				setStoreCode.append(oldMap.get("set_code").toString()).append(oldMap.get("store_code").toString()).toString();
//				//if(set_storeCode.toString()==setStoreCode.toString()){
//				if(set_storeCode.toString().equals(setStoreCode.toString())){
//					if("0".equals(oldMap.get("is_last").toString())){
//						amount_money = amount_money + Double.parseDouble(oldMap.get("money").toString());
//					}
//				}
//			}
//			m.put("amount_money", amount_money);
//			lastList.add(m);
//		}
//		return ChdJson.toJsonLower(lastList);
	}
	/**
	 * 其他入库明细查询
	 */
	@Override
	public String queryMatOtherInDetail(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherInDetail(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherInDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * 其他出库明细查询
	 */
	@Override
	public String queryMatOtherOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherOutDetail(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherOutDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatAccountReportDeptOutPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		if(entityMap.get("begin_date") != null && !"".equals(entityMap.get("begin_date").toString())){
			entityMap.put("begin_date", DateUtil.stringToDate(entityMap.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_date") != null && !"".equals(entityMap.get("end_date").toString())){
			entityMap.put("end_date", DateUtil.stringToDate(entityMap.get("end_date").toString(), "yyyy-MM-dd"));
		}
		List<Map<String, Object>> list = matAccountReportDeptOutMapper.queryMatAccountReportDeptOut(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatInByTypePrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> dblist = JsonListMapUtil.toListMapLower(matAccountReportDeptOutMapper.queryMatInByType(entityMap));
		List<Map<String,Object>> list = new  ArrayList<Map<String,Object>>();//非重List
		List<Map<String,Object>> returnList = new  ArrayList<Map<String,Object>>();//返回数据List
		//过滤虚库,仓库重复数据
		Set<String> setStoreCodeSet=new HashSet<String>();
		for (Map<String, Object> map : dblist) {
			String setStoreCode=map.get("set_code")+"@"+map.get("store_code");
			if (!setStoreCodeSet.contains(setStoreCode)) {
				setStoreCodeSet.add(setStoreCode);
				list.add(map);
			}
		}
		for (Map<String, Object> map : list) {
			//装填一行数据基础信息
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("set_name", map.get("set_name"));
			returnMap.put("set_code", map.get("set_code"));
			returnMap.put("store_name", map.get("store_name"));
			returnMap.put("store_code", map.get("store_code"));
			//装填此行数据  各个 物资类别  对应的总金额  以及汇总金额
			String returnSetStoreCode=returnMap.get("set_code")+"@"+returnMap.get("store_code");
			double sumAccount=0;
			for (Map<String, Object> accountByTypeMap : dblist) {
				String accountSetStoreCode=accountByTypeMap.get("set_code")+"@"+accountByTypeMap.get("store_code");
				if (returnSetStoreCode!=null && returnSetStoreCode.equals(accountSetStoreCode)) {
					//获取查询数据的金额
					double dbSum=Double.parseDouble(accountByTypeMap.get("money").toString());
					//获取查询数据的类别编码的主类别(前两位)
					String type=accountByTypeMap.get("mat_type_code").toString().substring(0, 2);
					//判断行数据map中是否含有此主类别,有则增加对应金额,没有再则行数据map中添加此类别
					if (returnMap.get(type)!=null) {
						double preSum=Double.parseDouble(returnMap.get(type).toString());
						returnMap.put(type, preSum+dbSum);
					}else{
						returnMap.put(type, dbSum);
					}
					//增加汇总金额
					sumAccount+=dbSum;
				}
			}
			returnMap.put("amount_money", sumAccount);
			returnList.add(returnMap);
		}
		return returnList;
	}

	@Override
	public List<Map<String, Object>> queryMatOtherInDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherInDetail(entityMap);			
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatOtherOutDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
			List<Map<String,Object>> list = matAccountReportDeptOutMapper.queryMatOtherOutDetail(entityMap);			
			return list;
	}
}
