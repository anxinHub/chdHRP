/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.books.allyear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.allyear.AccAllYearMapper;
import com.chd.hrp.acc.service.books.allyear.AccAllYearService;

/**
 * 全年账簿 
 * @author gaopei
 *
 */
@Service("accAllYearService")
public class AccAllYearServiceImpl implements AccAllYearService {

	private static Logger logger = Logger.getLogger(AccAllYearServiceImpl.class);


	@Resource(name = "accAllYearMapper")
	private final AccAllYearMapper accAllYearMapper = null; 

	/**
	 * 科目明细账 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAllYearBySubjDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("subjdata", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */
		 
		accAllYearMapper.collectAllYearBySubjDetail(entityMap);
		  
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("subjdata"));
	}
	
	/**
	 * 科目明细账   普通打印
	 */
	@Override
	public List<Map<String, Object>> collectAllYearBySubjDetailPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("subjdata", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */
		 
		accAllYearMapper.collectAllYearBySubjDetail(entityMap);
		  
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("subjdata");
		
		return resList;
		 
	}
	/**
	 * 科目明细账   模板打印
	 */
	@Override
	public Map<String, Object> collectAllYearBySubjDetailPrint( Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("subjdata", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */
		 
		accAllYearMapper.collectAllYearBySubjDetail(entityMap);
		  
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", (List<Map<String, Object>>)entityMap.get("subjdata"));

		return reMap; 
		
	}
	
	/**
	 * 项目辅助明细账   查询（例如 项目明细账）
	 */
	@Override
	public String collectAccZcheckDetail(Map<String, Object> entityMap)
			throws DataAccessException {
	
		entityMap.put("source_id", "0");
		
		accAllYearMapper.collectAccZcheckDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		//System.out.println(ChdJson.toJson((List<Map<String, Object>>)entityMap.get("objdata")));
		
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));
	}
	
	/**
	 * 项目辅助明细账   普通打印（例如 项目明细账）
	 */
	@Override
	public List<Map<String, Object>> collectAccZcheckDetailPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		
		accAllYearMapper.collectAccZcheckDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		return resList;
	}
	
	/**
	 * 项目辅助明细账   模板打印（例如 项目明细账）
	 */
	@Override
	public Map<String, Object> collectAccZcheckDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		
		accAllYearMapper.collectAccZcheckDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", (List<Map<String, Object>>)entityMap.get("rst"));

		return reMap; 
		
	}
	
	@Override
	public String collectBalanceLedgerDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
