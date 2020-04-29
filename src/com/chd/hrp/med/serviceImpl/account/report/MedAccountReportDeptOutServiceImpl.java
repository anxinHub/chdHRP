/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.med.dao.account.report.MedAccountReportDeptOutMapper;
import com.chd.hrp.med.service.account.report.MedAccountReportDeptOutService;
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
 


@Service("medAccountReportDeptOutService")
public class MedAccountReportDeptOutServiceImpl implements MedAccountReportDeptOutService {

	private static Logger logger = Logger.getLogger(MedAccountReportDeptOutServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountReportDeptOutMapper")
	private final MedAccountReportDeptOutMapper medAccountReportDeptOutMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountReportDeptOut(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountReportDeptOutMapper.queryMedAccountReportDeptOut(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMedInByType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medAccountReportDeptOutMapper.queryMedInByType(entityMap));
		List<Map<String,Object>> newlist = new  ArrayList<Map<String,Object>>();
		List<Map<String,Object>> lastList = new  ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list){
			Map<String, Object> newMap = new HashMap<String,Object>();
			newMap.put("set_name", map.get("set_name"));
			newMap.put("set_code", map.get("set_code"));
			newMap.put("store_name", map.get("store_name"));
			newMap.put("store_code", map.get("store_code"));
			newMap.put(map.get("med_type_code").toString(), map.get("money"));
			newMap.put(map.get("is_last").toString(), map.get("is_last"));
			newlist.add(newMap);
		}
		
		for(Map<String,Object> m : newlist){
			StringBuffer set_storeCode = new StringBuffer();
			set_storeCode.append(m.get("set_code").toString()).append(m.get("store_code").toString());
			
			Double amount_money = 0.00;
			for(Map<String,Object> oldMap : list){
				StringBuffer setStoreCode = new StringBuffer();
				setStoreCode.append(oldMap.get("set_code").toString()).append(oldMap.get("store_code").toString()).toString();
				//if(set_storeCode.toString()==setStoreCode.toString()){
				if(set_storeCode.toString().equals(setStoreCode.toString())){
					if("0".equals(oldMap.get("is_last").toString())){
						amount_money = amount_money + Double.parseDouble(oldMap.get("money").toString());
					}
				}
			}
			m.put("amount_money", amount_money);
			lastList.add(m);
		}
		
		return ChdJson.toJsonLower(lastList);
	}
	/**
	 * 其他入库明细查询
	 */
	@Override
	public String queryMedOtherInDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = medAccountReportDeptOutMapper.queryMedOtherInDetail(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medAccountReportDeptOutMapper.queryMedOtherInDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * 其他出库明细查询
	 */
	@Override
	public String queryMedOtherOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = medAccountReportDeptOutMapper.queryMedOtherOutDetail(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medAccountReportDeptOutMapper.queryMedOtherOutDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}
