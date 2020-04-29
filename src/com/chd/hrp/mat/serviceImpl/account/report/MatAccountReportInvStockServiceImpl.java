/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.MatAccountReportInvStockMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockService;

/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountReportInvStockService")
public class MatAccountReportInvStockServiceImpl implements MatAccountReportInvStockService {

	private static Logger logger = Logger.getLogger(MatAccountReportInvStockServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountReportInvStockMapper")
	private final MatAccountReportInvStockMapper matAccountReportInvStockMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountReportInvStock(Map<String,Object> entityMap) throws DataAccessException{
		/*entityMap.put("user_id", SessionManager.getUserId());
		matAccountReportInvStockMapper.queryMatAccountReportInvStock(entityMap);
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return ChdJson.toJson(list);*/
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public synchronized String collectMatAccountReportInvStock(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("user_id", SessionManager.getUserId());
		matAccountReportInvStockMapper.queryMatAccountReportInvStock(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportInvStock1(Map<String,Object> entityMap) throws DataAccessException{
		entityMap.put("user_id", SessionManager.getUserId());
		//存放结果集
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 
	 * 查询本期增加减少字段<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountReportInvStockColumns(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountReportInvStockMapper.queryMatAccountReportInvStockColumns(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectMatAccountReportInvStockPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		matAccountReportInvStockMapper.queryAccountReportInvStockPrint(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return list;
	}
}
