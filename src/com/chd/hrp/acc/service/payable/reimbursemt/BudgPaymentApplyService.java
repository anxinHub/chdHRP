package com.chd.hrp.acc.service.payable.reimbursemt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;

public interface BudgPaymentApplyService {
	public String addBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	 
	
	public String addBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteBudgPaymentApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchBudgPaymentApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateSubmitAndWithdraw(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateConfirmPay(List<Map<String, Object>> entityMap,Map<String, Object> paramMapVo)throws DataAccessException;
	
	public String queryBudgPaymentApply(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryBudgPaymentApplyDet(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public BudgPaymentApply queryBudgPaymentApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgPaymentApply queryBudgPaymentApplyByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrDept queryBudgBorrDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrProj queryBudgBorrProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrDetDept queryBudgBorrDetDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrDetProj queryBudgBorrDetProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryBorrPaymentApplyPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	//报销申请单  模板打印  PageOffice
	public Map<String, Object> queryBorrPaymentApplyPrintTemlateNew(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgPaymentApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String updateUnConfirmPay(List<Map<String, Object>> entityMap,Map<String, Object> paramMapVo)throws DataAccessException;
	

	public String queryUseApplyCode(Map<String, Object> mapVo);


	public String queryMoneyApplyDet(Map<String, Object> mapVo);


	public Map<String, Object> queryAddPageData(HashMap<String, Object> mapVo); 


	public String queryGetEmpCardNoSelect(Map<String, Object> mapVo);


	public Map<String, Object> queryBudgPro(HashMap<String, Object> mapData);

	/**
	 * 导入报销申请
	 */
	public String importBudgPaymentApply(Map<String, Object> paraMap) throws DataAccessException;
}
