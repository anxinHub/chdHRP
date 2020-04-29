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
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccProjAuxiliaryAccountMapper;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccProjAuxiliaryAccountService;

@Service("groupAccProjAuxiliaryAccountService")
public class GroupAccProjAuxiliaryAccountServiceImpl implements GroupAccProjAuxiliaryAccountService {

	private static Logger logger = Logger.getLogger(GroupAccProjAuxiliaryAccountServiceImpl.class);

	@Resource(name = "groupAccProjAuxiliaryAccountMapper")
	private final GroupAccProjAuxiliaryAccountMapper groupAccProjAuxiliaryAccountMapper = null;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccProjSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccProjSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {

		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjProjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjProjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccProjEndOs(Map<String, Object> entityMap) throws DataAccessException {
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("objdata"));

	}
	
	//项目科目总账 普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccProjSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {


		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 3);
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//项目科目总账 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccProjSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
	}

	//项目科目明细账 普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccProjSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 3);
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjDetailLedger(entityMap);
		
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
	
	//项目科目明细账 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccProjSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjSubjDetailLedger(entityMap);
		
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

	//科目项目总账 普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjProjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 3);
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		 
	}
	
	//科目项目总账 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjProjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

	//科目项目明细账 普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccSubjProjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 3);
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjDetailLedger(entityMap);
		
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
	
	//科目项目明细账 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjProjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccSubjProjDetailLedger(entityMap);
		
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

	//项目余额表 普通打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccProjEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		entityMap.put("table_flag", 3);
		
		groupAccProjAuxiliaryAccountMapper.queryGroupAccProjEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("objdata");
		 
		 return resList;
		
	}
	
	//项目余额表 模板打印
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccProjEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("objdata", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		reList=groupAccProjAuxiliaryAccountMapper.queryGroupAccProjEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", reList);
		
		return reMap;
		
	}

}
