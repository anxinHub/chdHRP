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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccDeptAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccDeptAuxiliaryAccountService;

@Service("groupAccDeptAuxiliaryAccountService")
public class GroupAccDeptAuxiliaryAccountServiceImpl implements GroupAccDeptAuxiliaryAccountService {

	@Resource(name = "groupAccDeptAuxiliaryAccountMapper")
	private final GroupAccDeptAuxiliaryAccountMapper groupAccDeptAuxiliaryAccountMapper = null;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccDeptSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccDeptSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjDeptGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjDeptDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccDeptBalance(Map<String, Object> entityMap) throws DataAccessException {

		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptBalance(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("objdata"));

	}
	
	//部门科目总账  表格打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccDeptSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 1);
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		
	}
	//部门科目总账   模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccDeptSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//部门科目明细账 表格打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccDeptSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 1);
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjDetailLedger(entityMap); 
		
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
	//部门科目明细账 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccDeptSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptSubjDetailLedger(entityMap); 
		
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

	//科目部门总账  表格打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjDeptGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 1);
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		return resList;
		 
	}
	//科目部门总账  模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjDeptGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目部门明细账  普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjDeptDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 1);
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptDetailLedger(entityMap); 
		
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
	//科目部门明细账  模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjDeptDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccSubjDeptDetailLedger(entityMap); 
		
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

	//部门余额表  普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccDeptBalancePrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 1);
		
		groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptBalance(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		
		return resList;
		
	}
	
	//部门余额表  模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccDeptBalancePrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccDeptAuxiliaryAccountMapper.queryGroupAccDeptBalance(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}
}
