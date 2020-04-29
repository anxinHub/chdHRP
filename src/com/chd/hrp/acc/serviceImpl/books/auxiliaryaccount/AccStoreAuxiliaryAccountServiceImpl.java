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
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccStoreAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccStoreAuxiliaryAccountService;

@Service("accStoreAuxiliaryAccountService")
public class AccStoreAuxiliaryAccountServiceImpl implements AccStoreAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(AccStoreAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "accStoreAuxiliaryAccountMapper")
	private final AccStoreAuxiliaryAccountMapper accStoreAuxiliaryAccountMapper = null;

	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 库房科目总账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectStoreSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fkf101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accStoreAuxiliaryAccountMapper.queryStoreSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  库房科目总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectStoreSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fkf101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accStoreAuxiliaryAccountMapper.queryStoreSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}

	/**
	 *  库房科目总账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectStoreSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy101");
		//entityMap.put("p_flag", "fkf101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryStoreSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		
		reMap.put("detail", resList);

		return reMap;

	}
	
	/**
	 * 库房科目明细账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccStoreSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy102");
		//entityMap.put("p_flag", "fkf102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accStoreAuxiliaryAccountMapper.queryAccStoreSubjDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  库房科目明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccStoreSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy102");
		//entityMap.put("p_flag", "fkf102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccStoreSubjDetailLedger(entityMap);

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

	/**
	 *  库房科目明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccStoreSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy102");
		//entityMap.put("p_flag", "fkf102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccStoreSubjDetailLedger(entityMap);

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
		reMap.put("detail", resList);
		return reMap;

	}
	
	/**
	 * 科目库房总账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjStoreGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fkf103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accStoreAuxiliaryAccountMapper.queryAccStoreSubjDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreGeneralLedger(entityMap);
		
	}

	/**
	 *  科目库房总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjStoreGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fkf103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}
	/**
	 *  科目库房总账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjStoreGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy103");
		//entityMap.put("p_flag", "fkf103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreGeneralLedger(entityMap)
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);

		return reMap;

	}

	
	
	/**
	 * 科目库房明细账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjStoreDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fkf104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 *  科目库房明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjStoreDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fkf104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreDetailLedger(entityMap);

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
	
	/**
	 *  科目库房明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjStoreDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fzdy104");
		//entityMap.put("p_flag", "fkf104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accStoreAuxiliaryAccountMapper.queryAccSubjStoreDetailLedger(entityMap);

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

		reMap.put("detail", resList);

		return reMap;

	}
	/**
	 * 库房余额表 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectStoreEndOs(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 库房余额表  普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectStoreEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("p_flag", "fgys105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accSupAuxiliaryAccountMapper.querySupEndOs(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		return resList;
		 
	}
	
	/**
	 * 库房余额表 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectStoreEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 4);
		entityMap.put("p_flag", "fkf105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		//accStoreAuxiliaryAccountMapper.queryStoreEndOs(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		reMap.put("detail", resList);
		return reMap;
		
	}
	
}
