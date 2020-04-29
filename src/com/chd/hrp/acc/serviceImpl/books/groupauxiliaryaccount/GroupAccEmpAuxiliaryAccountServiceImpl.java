package com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount;

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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccEmpAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccEmpAuxiliaryAccountService;

@Service("groupAccEmpAuxiliaryAccountService")
public class GroupAccEmpAuxiliaryAccountServiceImpl implements GroupAccEmpAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(GroupAccEmpAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "groupAccEmpAuxiliaryAccountMapper")
	private final GroupAccEmpAuxiliaryAccountMapper groupAccEmpAuxiliaryAccountMapper = null;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccEmpSubjGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccEmpSubjDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjEmpGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjEmpDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccEmpEndOs(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));
		
	}
	
	//职工科目总账 普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccEmpSubjGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 2);
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		return resList;
		
	}
	
	//职工科目总账 模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccEmpSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//职工科目明细账 普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccEmpSubjDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 2);
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
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
	//职工科目明细账 模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccEmpSubjDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		 
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
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

	//科目职工总账 普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjEmpGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 2);
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		return resList;
		
	}
	
	//科目职工总账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjEmpGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目职工明细账 普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjEmpDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 2);
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
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
	//科目职工明细账 模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjEmpDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccSubjEmpDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
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

	//职工余额表  普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccEmpEndOsPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 2);
		
		groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		return resList;
		
	}
	//职工余额表  普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccEmpEndOsPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccEmpAuxiliaryAccountMapper.queryGroupAccEmpEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}
	
}
