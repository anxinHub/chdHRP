package com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccDeptAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccHosAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.entity.AccHosAuxiliaryAccount;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccHosAuxiliaryAccountService;
import com.github.pagehelper.PageInfo;

@Service("accHosAuxiliaryAccountService")
public class AccHosAuxiliaryAccountServiceImpl implements AccHosAuxiliaryAccountService {
	
	private static Logger logger = Logger.getLogger(AccHosAuxiliaryAccountServiceImpl.class);
	
	@Resource(name = "accHosAuxiliaryAccountMapper")
	private final AccHosAuxiliaryAccountMapper accHosAuxiliaryAccountMapper = null;
	
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	/**
	 * 单位科目总账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectHosSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accHosAuxiliaryAccountMapper.queryHosSubjGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	/**
	 * 单位科目总账  表格打印
	 */
	@Override
	public List<Map<String, Object>> collectHosSubjGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryHosSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		 
		return resList;
	}
	
	/**
	 * 单位科目总账  模板打印
	 */
	@Override
	public Map<String, Object> collectHosSubjGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy101");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryHosSubjGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);
		
		return reMap;
		 
	}
	
	/**
	 * 单位科目明细账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccHosSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accHosAuxiliaryAccountMapper.queryAccHosSubjDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  单位科目明细账 表格打印
	 */
	@Override
	public List<Map<String, Object>> collectAccHosSubjDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryAccHosSubjDetailLedger(entityMap);

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
	 *  单位科目明细账 模板打印
	 */
	@Override
	public Map<String, Object> collectAccHosSubjDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy102");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryAccHosSubjDetailLedger(entityMap);
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
	 * 科目单位总账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjHosGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accHosAuxiliaryAccountMapper.queryAccSubjHosGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  科目单位总账 表格打印
	 */
	@Override
	public List<Map<String, Object>> collectAccSubjHosGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryAccSubjHosGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;
	}

	/**
	 *  科目单位总账 模板打印
	 */
	@Override
	public Map<String, Object> collectAccSubjHosGeneralLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy103");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryAccSubjHosGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);

		return reMap;
	}
	
	/**
	 * 科目单位明细账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjHosDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accHosAuxiliaryAccountMapper.queryAccSubjHosDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  科目单位明细账账 表格打印
	 */
	@Override
	public List<Map<String, Object>> collectAccSubjHosDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryAccSubjHosDetailLedger(entityMap);

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
	 *  科目单位明细账账 模板打印
	 */
	@Override
	public Map<String, Object> collectAccSubjHosDetailLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fzdy104");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		//accHosAuxiliaryAccountMapper.queryAccSubjHosDetailLedger(entityMap);
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
	 * 单位余额表 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectHosEndOs(Map<String, Object> entityMap) throws DataAccessException {

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
	 *  单位余额表 表格打印
	 */
	@Override
	public List<Map<String, Object>> collectHosEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {

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
	 *  单位余额表 模板打印
	 */
	@Override
	public Map<String, Object> collectHosEndOsPrintDate(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", 8);
		entityMap.put("p_flag", "fdw105");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accHosAuxiliaryAccountMapper.queryHosEndOs(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");
		reMap.put("detail", resList);
		return reMap;
	}
	
	
}
