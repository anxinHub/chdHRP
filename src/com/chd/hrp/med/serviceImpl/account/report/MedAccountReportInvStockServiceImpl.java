/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.MedAccountReportInvStockMapper;
import com.chd.hrp.med.service.account.report.MedAccountReportInvStockService;

/**
 * 
 * @Description:
 * 药品库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountReportInvStockService")
public class MedAccountReportInvStockServiceImpl implements MedAccountReportInvStockService {

	private static Logger logger = Logger.getLogger(MedAccountReportInvStockServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountReportInvStockMapper")
	private final MedAccountReportInvStockMapper medAccountReportInvStockMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountReportInvStock(Map<String,Object> entityMap) throws DataAccessException{
		entityMap.put("user_id", SessionManager.getUserId());
		medAccountReportInvStockMapper.queryMedAccountReportInvStock(entityMap);
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultData");
		return ChdJson.toJson(list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectMedAccountReportInvStock(Map<String,Object> entityMap) throws DataAccessException{
		entityMap.put("user_id", SessionManager.getUserId());
		medAccountReportInvStockMapper.queryMedAccountReportInvStock(entityMap);
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
	public String queryMedAccountReportInvStock1(Map<String,Object> entityMap) throws DataAccessException{
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
	public String queryMedAccountReportInvStockColumns(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountReportInvStockMapper.queryMedAccountReportInvStockColumns(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}
