package com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccCusAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccCusAuxiliaryAccountService;
@Service("groupAccCusAuxiliaryAccountService")
public class GroupAccCusAuxiliaryAccountServiceImpl implements GroupAccCusAuxiliaryAccountService {
	
	@Resource(name = "groupAccCusAuxiliaryAccountMapper")
	private final GroupAccCusAuxiliaryAccountMapper groupAccCusAuxiliaryAccountMapper = null;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccCusSubjGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccCusSubjDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjCusGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjCusDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccCusEndOs(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));

	}
	
	//客户科目总账     普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccCusSubjGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 5);
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		
	}
	
	//客户科目总账     模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccCusSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//客户科目明细账     普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccCusSubjDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 5);
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjDetailLedger(entityMap); 
		
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
	
	//客户科目明细账       模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccCusSubjDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusSubjDetailLedger(entityMap); 
		
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

	//科目客户总账     普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjCusGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 5);
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		
	}
	
	//科目客户总账       模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjCusGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目客户明细账     普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjCusDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 5);
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusDetailLedger(entityMap); 
		
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
	
	//科目客户明细账     模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjCusDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccSubjCusDetailLedger(entityMap); 
		
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

	//客户余额表     普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccCusEndOsPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 5);
		
		groupAccCusAuxiliaryAccountMapper.queryGroupAccCusEndOs(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		
	}
	
	//客户余额表      模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccCusEndOsPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccCusAuxiliaryAccountMapper.queryGroupAccCusEndOs(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}
	
}
