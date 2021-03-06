﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.reportforms.query;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.reportforms.query.BudgMedInQueryMapper;
import com.chd.hrp.budg.service.budgincome.reportforms.query.BudgMedInQueryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院及科室 医疗收入预算  报表查询
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR  BUDG_MED_INCOME_HOS_MONTH  BUDG_MED_INCOME_DEPT_MONTH   BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedInQueryService")
public class BudgMedInQueryServiceImpl implements BudgMedInQueryService {

	private static Logger logger = Logger.getLogger(BudgMedInQueryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedInQueryMapper")
	private final BudgMedInQueryMapper budgMedInQueryMapper = null;
    
	/**
	 * @Description 
	 * 医院医疗收入预算 报表查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedInHosBudg(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInHosBudg(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInHosBudg(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 科室医疗收入预算 报表查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedInDeptBudg(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInDeptBudg(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInDeptBudg(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryMedInQueryPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInHosBudg(entityMap);

		return list;
	}

	@Override
	public List<Map<String, Object>> queryHosMedInQueryPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInQueryMapper.queryMedInHosBudg(entityMap);
		
		return list;
	}
	
}
