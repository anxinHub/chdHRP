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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccStoreAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccStoreAuxiliaryAccountService;

@Service("groupAccStoreAuxiliaryAccountService")
public class GroupAccStoreAuxiliaryAccountServiceImpl implements GroupAccStoreAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(GroupAccStoreAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "groupAccStoreAuxiliaryAccountMapper")
	private final GroupAccStoreAuxiliaryAccountMapper groupAccStoreAuxiliaryAccountMapper = null;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccStoreSubjGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccStoreSubjDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjStoreGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}
	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjStoreDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccStoreEndOs(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));
		
	}
	
	//库房科目总账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccStoreSubjGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 4);
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//库房科目总账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccStoreSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//库房科目明细账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccStoreSubjDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 5);
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjDetailLedger(entityMap);
		
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
	
	//库房科目明细账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccStoreSubjDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreSubjDetailLedger(entityMap);
		
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

	//科目库房总账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjStoreGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 4);
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//科目库房总账   模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjStoreGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目库房明细账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjStoreDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 4);
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreDetailLedger(entityMap);
		
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
	
	//科目库房明细账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjStoreDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccSubjStoreDetailLedger(entityMap);
		
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

	//库房余额表  普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccStoreEndOsPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 4);
		
		groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//库房余额表 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupStoreEndOsPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccStoreAuxiliaryAccountMapper.queryGroupAccStoreEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}
	
}
