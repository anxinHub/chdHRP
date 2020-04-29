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
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccProjAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccProjAuxiliaryAccountService;

@Service("accProjAuxiliaryAccountService")
public class AccProjAuxiliaryAccountServiceImpl implements AccProjAuxiliaryAccountService {

	private static Logger logger = Logger.getLogger(AccProjAuxiliaryAccountServiceImpl.class);

	@Resource(name = "accProjAuxiliaryAccountMapper")
	private final AccProjAuxiliaryAccountMapper accProjAuxiliaryAccountMapper = null;

	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 项目科目总账 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectProjSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accProjAuxiliaryAccountMapper.queryProjSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 项目科目总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectProjSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accProjAuxiliaryAccountMapper.queryProjSubjGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}

	/**
	 * 项目科目总账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectProjSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		List<Map<String, Object>> reList = (List<Map<String, Object>>) entityMap.get("rst");

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", reList);

		return reMap;
	}
	
	/**
	 * 项目科目明细账 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccProjSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accProjAuxiliaryAccountMapper.queryAccProjSubjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 * 项目科目明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccProjSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accProjAuxiliaryAccountMapper.queryAccProjSubjDetailLedger(entityMap);
		
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
	 * 项目科目明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccProjSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accProjAuxiliaryAccountMapper.queryAccProjSubjDetailLedger(entityMap);
		
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
	 * 科目项目总账
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjProjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accProjAuxiliaryAccountMapper.queryAccSubjProjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  科目项目总账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjProjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accProjAuxiliaryAccountMapper.queryAccSubjProjGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		return resList;

	}

	/**
	 *  科目项目总账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjProjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy103");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//accProjAuxiliaryAccountMapper.queryAccSubjProjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);

		return reMap;

	}
	
	/**
	 * 科目项目明细账
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjProjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accProjAuxiliaryAccountMapper.queryAccSubjProjDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  科目项目明细账 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjProjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accProjAuxiliaryAccountMapper.queryAccSubjProjDetailLedger(entityMap);

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
	 *  科目项目明细账 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjProjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 3);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accProjAuxiliaryAccountMapper.queryAccSubjProjDetailLedger(entityMap);

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
	 * 项目余额表
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectProjEndOs(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 项目余额表 普通打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectProjEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {

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
	 * 项目余额表 模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectProjEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 1);
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fxm105");
		entityMap.put("check_item_type", "");
		entityMap.put("check_item_code", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accProjAuxiliaryAccountMapper.queryProjEndOs(entityMap);
		//List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);
		
		return reMap;
		
	}

}
