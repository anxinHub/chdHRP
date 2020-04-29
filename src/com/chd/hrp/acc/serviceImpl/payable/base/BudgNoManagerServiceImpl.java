package com.chd.hrp.acc.serviceImpl.payable.base;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
@Service("budgNoManagerService")
public class BudgNoManagerServiceImpl implements BudgNoManagerService {
	
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;

	@Override
	public int updateBudgNoManagerMaxNo(Map<String, Object> entityMap) throws DataAccessException {
		return budgNoManagerMapper.updateBudgNoManagerMaxNo(entityMap);
	}

	@Override
	public BudgNoManager queryBudgNoManagerByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("table_code") != null) {
			entityMap.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		}
		BudgNoManager bp=budgNoManagerMapper.queryBudgNoManagerByCode(entityMap);
		if(bp==null){
			entityMap.put("max_no", 0);
			entityMap.put("seq_no", 4);
			budgNoManagerMapper.addBudgNoManager(entityMap);
		}
		
		return budgNoManagerMapper.queryBudgNoManagerByCode(entityMap);
	}

	@Override
	public String getBillNOSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("table_code") != null) {
			entityMap.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		}
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = cal.get(Calendar.MONTH) + 1;
		String strMonth = "";
		
		if(month < 10){
			strMonth = strMonth + "0" + month;
		}else{
			strMonth = strMonth + String.valueOf(month);
		}
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("year", year);
		entityMap.put("month", strMonth);
		entityMap.put("table_name", entityMap.get("table_name"));
		entityMap.put("prefixe", entityMap.get("prefixe"));
		BudgNoManager budgNoManager = queryBudgNoManagerByName(entityMap);
		String pref = budgNoManager.getPrefixe();
		int seq_no = budgNoManager.getSeq_no();
		int max_no = budgNoManager.getMax_no();
		return pref + year + strMonth + Strings.alignRight(max_no, seq_no, '0');
	}

	@Override
	public String getBillNOSeqNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = cal.get(Calendar.MONTH) + 1;
		String strMonth = "";
		
		if(month < 10){
			strMonth = strMonth + "0" + month;
		}else{
			strMonth = strMonth + String.valueOf(month);
		}
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("year", year);
		entityMap.put("month", strMonth);
		entityMap.put("table_code", tableName.toUpperCase());
		entityMap.put("table_name", entityMap.get("table_name"));
		entityMap.put("prefixe", entityMap.get("prefixe"));
		BudgNoManager budgNoManager = queryBudgNoManagerByName(entityMap);
		
		String pref = budgNoManager.getPrefixe();
		int seq_no = budgNoManager.getSeq_no();
		int max_no = budgNoManager.getMax_no();
		return pref + year + strMonth + Strings.alignRight(max_no, seq_no, '0');
	}

	@Override
	public int updateBudgNoManagerMaxNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = cal.get(Calendar.MONTH) + 1;
		String strMonth = "";
		
		if(month < 10){
			strMonth = strMonth + "0" + month;
		}else{
			strMonth = strMonth + String.valueOf(month);
		}
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("year", year);
		entityMap.put("month", strMonth);
		entityMap.put("table_code", tableName);
		return budgNoManagerMapper.updateBudgNoManagerMaxNo(entityMap);
	}

}
