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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccSupAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccSupAuxiliaryAccountService;

@Service("groupAccSupAuxiliaryAccountService")
public class GroupAccSupAuxiliaryAccountServiceImpl implements GroupAccSupAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(GroupAccSupAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "groupAccSupAuxiliaryAccountMapper")
	private final GroupAccSupAuxiliaryAccountMapper groupAccSupAuxiliaryAccountMapper = null;
    
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSupSubjGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSupSubjDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjSupGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjSupDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSupEndOs(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));
		
	}
	
	//供应商科目总账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSupSubjGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 6);
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//供应商科目总账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSupSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//供应商科目明细账   普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSupSubjDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 6);
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjDetailLedger(entityMap);
		
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
	
	//供应商科目明细账   模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSupSubjDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupSubjDetailLedger(entityMap);
		
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

	//科目供应商总账  普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjSupGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 6);
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//科目供应商总账  模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjSupGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目供应商明细账   普通打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjSupDetailLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupDetailLedger(entityMap);
		
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
	
	//科目供应商明细账   模板打印
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectGroupAccSubjSupDetailLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSubjSupDetailLedger(entityMap);
		
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

	//供应商余额表   普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccSupEndOsPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 6);
		
		groupAccSupAuxiliaryAccountMapper.queryGroupAccSupEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//供应商余额表   模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSupEndOsPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccSupAuxiliaryAccountMapper.queryGroupAccSupEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}
	
}
