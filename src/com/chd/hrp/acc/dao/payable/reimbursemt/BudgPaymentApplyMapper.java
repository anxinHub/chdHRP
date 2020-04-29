package com.chd.hrp.acc.dao.payable.reimbursemt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;

public interface BudgPaymentApplyMapper extends SqlMapper{
	public int addBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateSubmitAndWithdraw(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<BudgPaymentApply> queryBudgPaymentApply(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<BudgPaymentApply> queryBudgPaymentApply(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public BudgPaymentApply queryBudgPaymentApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgPaymentApply queryBudgPaymentApplyByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAmount(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateConfirmPay(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryPaymentApplyByPrintTemlate(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgPaymentApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> queryBudgPaymentApplyByBusiType(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBudgPaymentApplyByBusiType(Map<String,Object> entityMap) throws DataAccessException;
	
	public BudgPaymentApply queryBudgPaymentApplyState(Map<String,Object> entityMap)throws DataAccessException;
	

	public List<Map<String, Object>> queryUseApplyCode(Map<String, Object> entityMap);


	public List<Map<String, Object>> queryMoneyApplyDet(Map<String, Object> mapVo);


	public Map<String, Object> queryAddPageData(HashMap<String, Object> mapVo);


	public List<Map<String, Object>> queryGetEmpCardNoSelect(Map<String, Object> entityMap);

	public Map<String, Object> queryBudgPro(HashMap<String, Object> mapData);

	public List<Map<String, Object>> queryAccountListByEmpIdList(List<Map<String, Object>> list) throws DataAccessException;
}	
