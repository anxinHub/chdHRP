package com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount;

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
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccDeptAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccDeptAuxiliaryAccountService;

@Service("accDeptAuxiliaryAccountService")
public class AccDeptAuxiliaryAccountServiceImpl implements AccDeptAuxiliaryAccountService {

	@Resource(name = "accDeptAuxiliaryAccountMapper")
	private final AccDeptAuxiliaryAccountMapper accDeptAuxiliaryAccountMapper = null;
	
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 部门辅助账  部门科目总账
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectDeptSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fbm101");
		
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
	 * 部门科目总账   表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectDeptSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fbm101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryDeptSubjGeneralLedger(entityMap); 
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			 
		return resList;
			
	}
	/**
	 * 部门科目总账   模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectDeptSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fbm101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//accDeptAuxiliaryAccountMapper.queryDeptSubjGeneralLedger(entityMap); 
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		reMap.put("detail", resList);
			
		return reMap;
			
	}
	
	/**
	 * 部门辅助账  部门科目明细账	
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccDeptSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accDeptAuxiliaryAccountMapper.queryAccDeptSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}
	
	/**
	 * 部门辅助账  部门科目明细账打印	
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccDeptSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccDeptSubjDetailLedger(entityMap); 
		
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
	 * 部门科目明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccDeptSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccDeptSubjDetailLedger(entityMap); 
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
	 * 科目部门总账  查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjDeptGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 科目部门总账  表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjDeptGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptGeneralLedger(entityMap);
			
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		return resList;
		
	}
	
	/**
	 * 科目部门总账  模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjDeptGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptGeneralLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);
		return reMap;
		
	}

	
	/**
	 * 科目部门明细账
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjDeptDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 科目部门明细账  普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjDeptDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptDetailLedger(entityMap); 
		
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
	 * 科目部门明细账  模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjDeptDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccSubjDeptDetailLedger(entityMap); 
		
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
	 * 部门余额表查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccDeptBalance(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 部门余额表  普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccDeptBalancePrint(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 部门余额表  模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccDeptBalancePrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fbm105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accDeptAuxiliaryAccountMapper.queryAccDeptBalance(entityMap); 
		
		List<Map<String, Object>> reList=(List<Map<String, Object>>) entityMap.get("rst");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		reMap.put("detail", reList);
		return reMap;
		
	}
}
