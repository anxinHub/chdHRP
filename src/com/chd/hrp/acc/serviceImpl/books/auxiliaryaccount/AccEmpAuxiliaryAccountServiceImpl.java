package com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount;

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
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccDeptAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccEmpAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccEmpAuxiliaryAccountService;

@Service("accEmpAuxiliaryAccountService")
public class AccEmpAuxiliaryAccountServiceImpl implements
		AccEmpAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(AccEmpAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "accEmpAuxiliaryAccountMapper")
	private final AccEmpAuxiliaryAccountMapper accEmpAuxiliaryAccountMapper = null;
	
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 职工科目总账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectEmpSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accDeptAuxiliaryAccountMapper.queryDeptSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 职工科目总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectEmpSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy101");
			
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			
		//accEmpAuxiliaryAccountMapper.queryEmpSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		return resList;
			
	}
	/**
	 * 职工科目总账 模板打印	
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectEmpSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			entityMap.put("source_id", "0");
			entityMap.put("table_flag", 2);
			entityMap.put("p_flag", "fzdy101");
			
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			
			//accEmpAuxiliaryAccountMapper.queryEmpSubjGeneralLedger(entityMap);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			reMap.put("detail", resList);
			return reMap;
			
	}
	/**
	 * 职工科目明细账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccEmpSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accEmpAuxiliaryAccountMapper.queryAccEmpSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 职工科目明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccEmpSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accEmpAuxiliaryAccountMapper.queryAccEmpSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		String vouch_no = null ;
		for(Map<String, Object> map :resList){
			if(map.get("VOUCH_NO")!=null){ 
				//  重新封装凭证号
				vouch_no = map.get("VOUCH_NO").toString().split("-")[0]+"-"+map.get("VOUCH_NO").toString().split("-")[1];
				listMap.put("VOUCH_NO", vouch_no);
				map.remove("VOUCH_NO");
				map.put("VOUCH_NO", listMap.get("VOUCH_NO"));
			} 
			listResult.add(map);
		} 
		return listResult;
	}
	
	/**
	 * 职工科目明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectAccEmpSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			 
			Map<String,Object> reMap=new HashMap<String,Object>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			entityMap.put("source_id", "0");
			entityMap.put("table_flag", 2);
			entityMap.put("p_flag", "fzdy102");
			
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			//accEmpAuxiliaryAccountMapper.queryAccEmpSubjDetailLedger(entityMap);
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			String vouch_no = null ;
			for(Map<String, Object> map :resList){
				if(map.get("VOUCH_NO")!=null){ 
					//  重新封装凭证号
					vouch_no = map.get("VOUCH_NO").toString().split("-")[0]+"-"+map.get("VOUCH_NO").toString().split("-")[1];
					listMap.put("VOUCH_NO", vouch_no);
					map.remove("VOUCH_NO");
					map.put("VOUCH_NO", listMap.get("VOUCH_NO"));
				} 
				listResult.add(map);
			} 
			reMap.put("detail", resList);
			return reMap;
	}
	/**
	 * 科目职工总账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjEmpGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accEmpAuxiliaryAccountMapper.queryAccSubjEmpGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}
	
	/**
	 * 科目职工总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccSubjEmpGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accEmpAuxiliaryAccountMapper.queryAccSubjEmpGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		return resList;
			
	}
		
	/**
	 * 科目职工总账  模板打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectAccSubjEmpGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			
		//accEmpAuxiliaryAccountMapper.queryAccSubjEmpGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");	
		reMap.put("detail", resList);
			
		return reMap;
			
	}
		
	/**
	 * 科目职工明细账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjEmpDetailLedger(Map<String, Object> entityMap)  throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accEmpAuxiliaryAccountMapper.queryAccSubjEmpDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));

	}
	
	/**
	 * 科目职工明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccSubjEmpDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzdy104");
		
		//accEmpAuxiliaryAccountMapper.queryAccSubjEmpDetailLedger(entityMap);
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		String vouch_no = null ;
		for(Map<String, Object> map :resList){
			if(map.get("VOUCH_NO")!=null){ 
				//  重新封装凭证号
				vouch_no = map.get("VOUCH_NO").toString().split("-")[0]+"-"+map.get("VOUCH_NO").toString().split("-")[1];
				listMap.put("VOUCH_NO", vouch_no);
				map.remove("VOUCH_NO");
				map.put("VOUCH_NO", listMap.get("VOUCH_NO"));
			} 
			listResult.add(map);
		} 
		return resList; 
	}
	
	/**
	 * 科目职工明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectAccSubjEmpDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			
			List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			entityMap.put("source_id", "0");
			entityMap.put("table_flag", 2);
			entityMap.put("p_flag", "fzdy104");
			
			//accEmpAuxiliaryAccountMapper.queryAccSubjEmpDetailLedger(entityMap);
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			//accEmpAuxiliaryAccountMapper.queryAccSubjEmpDetailLedger(entityMap);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			String vouch_no = null ;
			for(Map<String, Object> map :resList){
				if(map.get("VOUCH_NO")!=null){ 
					//  重新封装凭证号
					vouch_no = map.get("VOUCH_NO").toString().split("-")[0]+"-"+map.get("VOUCH_NO").toString().split("-")[1];
					listMap.put("VOUCH_NO", vouch_no);
					map.remove("VOUCH_NO");
					map.put("VOUCH_NO", listMap.get("VOUCH_NO"));
				} 
				listResult.add(map);
			} 
			reMap.put("detail", resList);
			return reMap;
	}
		
	/**
	 * 职工余额表
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectEmpEndOs(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("p_flag", "fzdy105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}
	
	/**
	 * 职工余额表  普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectEmpEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.querySupEndOs(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		return resList;
		 
	}
	
	
	/**
	 * 职工余额表 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectEmpEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 2);
		entityMap.put("p_flag", "fzg105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccDeptBalance(entityMap); 
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> reList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", reList);
		return reMap;
			
	}	

	
	
}
