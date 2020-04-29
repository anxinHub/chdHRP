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
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccZcheckAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.entity.AccSupAuxiliaryAccount;
import com.chd.hrp.acc.service.books.auxiliaryaccount.AccZcheckAuxiliaryAccountService;
;
;
;
@Service("accZcheckAuxiliaryAccountService")
public class AccZcheckAuxiliaryAccountServiceImpl implements AccZcheckAuxiliaryAccountService {
	
	@Resource(name = "accZcheckAuxiliaryAccountMapper")
	private final AccZcheckAuxiliaryAccountMapper accZcheckAuxiliaryAccountMapper = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
	
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;
	
	/**
	 * 核算项科目总账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccZcheckGeneralLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy101");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accZcheckAuxiliaryAccountMapper.collectAccZcheckGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	
	}

	/**
	 *  核算项科目总账 打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccZcheckGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy101");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accZcheckAuxiliaryAccountMapper.collectAccZcheckGeneralLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}

	/**
	 * 核算项科目明细账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccZcheckDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy102");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accZcheckAuxiliaryAccountMapper.collectAccZcheckDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 *  核算项科目明细账 打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccZcheckDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> listMap = new HashMap<String, Object>();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy102");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accZcheckAuxiliaryAccountMapper.collectAccZcheckDetailLedger(entityMap);
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
	 * 科目核算项总账 查询
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjZcheckGeneralLedger( Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy103");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accZcheckAuxiliaryAccountMapper.collectAccSubjZcheckGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 *  科目核算项总账 打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjZcheckGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy103");
		entityMap.put("is_show", "1");
		entityMap.put("emp_code", "");
		
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		//accZcheckAuxiliaryAccountMapper.collectAccSubjZcheckGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}
	
	/**
	 * 科目核算项明细账
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectAccSubjZcheckDetailLedger(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy104");
		entityMap.put("emp_code", "");
		
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){
			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		//accZcheckAuxiliaryAccountMapper.collectAccSubjZcheckDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}

	/**
	 *  科目核算项明细账  打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccSubjZcheckDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		entityMap.put("table_flag", "7");
		entityMap.put("summary", "");
		entityMap.put("vouch_no_begin", "");
		entityMap.put("vouch_no_end", "");
		entityMap.put("subj_level", "");
		entityMap.put("p_flag", "fzdy104");
		entityMap.put("emp_code", "");
		
		entityMap.put("obj_code", "0");
		entityMap.put("obj_select_flag", "");
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		//accZcheckAuxiliaryAccountMapper.collectAccSubjZcheckDetailLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}
	
	/**
	 * 自定义科目核算余额表
	 */
	@Override
	public String collectAccZcheckEndOs(Map<String, Object> entityMap)throws DataAccessException {

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
	 
	 //打印科目核算项余额表 2017-09-18
	 @Override
	 @Transactional(rollbackFor = Exception.class)
	 public List<Map<String, Object>> collectAccSubjZcheckEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {

			entityMap.put("source_id", "0");
			entityMap.put("p_flag", "fzdy105");
			
			entityMap.put("rst", new ArrayList<Map<String, Object>>());
			accSubjLedgerMapper.collectAccSubjLedger(entityMap);

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

			return resList;
	 }
}
