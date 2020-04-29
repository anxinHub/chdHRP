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
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccSupAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccSupAuxiliaryAccountService;

@Service("accSupAuxiliaryAccountService")
public class AccSupAuxiliaryAccountServiceImpl implements AccSupAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(AccSupAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "accSupAuxiliaryAccountMapper")
	private final AccSupAuxiliaryAccountMapper accSupAuxiliaryAccountMapper = null;
    
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 供应商科目总账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectSupSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fgys101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accSupAuxiliaryAccountMapper.querySupSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	
	}

	/**
	 * 供应商科目总账  普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectSupSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fgys101");
			
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.querySupSubjGeneralLedger(entityMap);
			
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		return resList;
			 
	}
	
	/*
	 * 供应商科目总账  模板打印(non-Javadoc)
	 * @see com.chd.hrp.acc.service.books.auxiliaryaccount.AccSupAuxiliaryAccountService#collectSupSubjGeneralLedgerPrintDate(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectSupSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fgys101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//reList=accSupAuxiliaryAccountMapper.querySupSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);
		
		return reMap;
		
	}
	
	/**
	 * 供应商科目明细账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSupSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy102");
		//entityMap.put("p_flag", "fgys102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accSupAuxiliaryAccountMapper.queryAccSupSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 * 供应商科目明细账   普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccSupSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			Map<String, Object> listMap = new HashMap<String, Object>();
			List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			entityMap.put("source_id", "0");
			entityMap.put("table_flag", 6);
			entityMap.put("p_flag", "fzdy102");
			//entityMap.put("p_flag", "fgys102");
			
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			//accSupAuxiliaryAccountMapper.queryAccSupSubjDetailLedger(entityMap);
			
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
	 * 供应商科目明细账   模板打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectAccSupSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			
			entityMap.put("source_id", "0");
			entityMap.put("table_flag", 6);
			entityMap.put("p_flag", "fzdy102");
			//entityMap.put("p_flag", "fgys102");
			
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);
			//accSupAuxiliaryAccountMapper.queryAccSupSubjDetailLedger(entityMap);
			
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
	 * 科目供应商总账  查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjSupGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fgys103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accSupAuxiliaryAccountMapper.queryAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 科目供应商总账  普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccSubjSupGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fgys103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.queryAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			 
		return resList;
			 
	}
		
	/**
	 * 科目供应商总账  模板打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> collectAccSubjSupGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fgys103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.queryAccSubjSupGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");	
		reMap.put("detail", resList);
			
		return reMap;
			
	}
	/**
	 * 科目供应商明细账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjSupDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fgys104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accSupAuxiliaryAccountMapper.queryAccSubjSupDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 科目供应商明细账   普通打印
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectAccSubjSupDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fgys104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.queryAccSubjSupDetailLedger(entityMap);
			
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		String vouch_no = null;
		for (Map<String, Object> map : resList) {
			if (map.get("VOUCH_NO") != null) {
				// 重新封装凭证号
				vouch_no = map.get("VOUCH_NO").toString().split("-")[0] + "-" + map.get("VOUCH_NO").toString().split("-")[1];
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
	public Map<String, Object> collectAccSubjSupDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
			
		Map<String,Object> reMap=new HashMap<String,Object>();
			
		Map<String, Object> listMap = new HashMap<String, Object>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 6);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fgys104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.queryAccSubjSupDetailLedger(entityMap);
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		
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
	 * 供应商余额表
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectSupEndOs(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 供应商余额表   普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectSupEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {
		
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
	 * 供应商余额表   模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectSupEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fgys105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.querySupEndOs(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> reList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", reList);
		
		return reMap;
		
	}
	
}
