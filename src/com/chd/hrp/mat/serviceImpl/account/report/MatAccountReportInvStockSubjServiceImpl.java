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
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.MatAccountReportInvStockSubjMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockService;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockSubjService;

/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountReportInvStockSubjService")
public class MatAccountReportInvStockSubjServiceImpl implements MatAccountReportInvStockSubjService {

	private static Logger logger = Logger.getLogger(MatAccountReportInvStockSubjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountReportInvStockSubjMapper")
	private final MatAccountReportInvStockSubjMapper matAccountReportInvStockSubjMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	/*@Override
	@Transactional(rollbackFor = Exception.class)
	public String queryMatAccountReportInvStockSubj(Map<String,Object> entityMap) throws DataAccessException{
		entityMap.put("user_id", SessionManager.getUserId());
		matAccountReportInvStockSubjMapper.queryMatAccountReportInvStockSubj(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return ChdJson.toJson(list);
	}*/
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectMatAccountReportInvStockSubj(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = null;
		try {
			entityMap.put("user_id", SessionManager.getUserId());
			
			matAccountReportInvStockSubjMapper.querycollectMatAccountReportInvStockSubj(entityMap);
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			list = (List<Map<String, Object>>) entityMap.get("resultData");
			
		} catch (NoTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
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
	public String queryMatAccountReportInvStockSubjColumns(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountReportInvStockSubjMapper.queryMatAccountReportInvStockSubjColumns(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectMatAccountReportInvStockSubjPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		matAccountReportInvStockSubjMapper.queryAccountReportInvStockSubjPrint(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return list;
	}
}
