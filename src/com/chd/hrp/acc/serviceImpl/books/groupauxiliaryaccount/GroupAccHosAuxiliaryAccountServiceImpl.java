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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccHosAuxiliaryAccountMapper;
import com.chd.hrp.acc.entity.AccHosAuxiliaryAccount;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccHosAuxiliaryAccountService;

@Service("groupAccHosAuxiliaryAccountService")
public class GroupAccHosAuxiliaryAccountServiceImpl implements GroupAccHosAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(GroupAccHosAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "groupAccHosAuxiliaryAccountMapper")
	private final GroupAccHosAuxiliaryAccountMapper groupAccHosAuxiliaryAccountMapper = null;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccHosSubjGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccHosAuxiliaryAccount>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccHosSubjDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccHosAuxiliaryAccount>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjHosGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccHosAuxiliaryAccount>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjHosDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccHosAuxiliaryAccount>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccHosEndOs(Map<String, Object> entityMap)
			throws DataAccessException {
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));
		
	}

	//单位科目总账  表格打印
	@Override
	public List<Map<String, Object>> collectGroupAccHosSubjGeneralLedgerPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
	}
	//单位科目总账  模板打印
	@Override
	public Map<String, Object> collectGroupAccHosSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		reMap.put("detail", resList);
		
		return reMap;
		 
	}

	//单位科目明细账  表格打印
	@Override
	public List<Map<String, Object>> collectGroupAccHosSubjDetailLedgerPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjDetailLedger(entityMap);
		
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
	//单位科目明细账  模板打印
	@Override
	public Map<String, Object> collectGroupAccHosSubjDetailLedgerPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosSubjDetailLedger(entityMap);
		
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

	//科目单位总账  表格打印
	@Override
	public List<Map<String, Object>> collectGroupAccSubjHosGeneralLedgerPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 8);
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
	}
	
	//科目单位总账  模板打印
	@Override
	public  Map<String, Object> collectGroupAccSubjHosGeneralLedgerPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		 
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		reMap.put("detail", resList);
		
		return reMap;
	}

	//科目单位明细账账  表格打印
	@Override
	public List<Map<String, Object>> collectGroupAccSubjHosDetailLedgerPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 8);
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosDetailLedger(entityMap);
		
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
	//科目单位明细账账  模板打印
	@Override
	public  Map<String, Object>  collectGroupAccSubjHosDetailLedgerPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		 
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccSubjHosDetailLedger(entityMap);
		
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

	//单位余额表  表格打印
	@Override
	public List<Map<String, Object>> collectGroupAccHosEndOsPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 8);
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
	}
	//单位余额表  模板打印
	@Override
	public  Map<String, Object>  collectGroupAccHosEndOsPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		groupAccHosAuxiliaryAccountMapper.queryGroupAccHosEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		reMap.put("detail", resList);
		
		return reMap;
	}
	
}
