package com.chd.hrp.acc.service.payable.otherpay;
 
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgChargeApply;

public interface BudgChargeApplyService {
	
	//添加
	public String addBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//修改
	public String updateBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//删除
	public String deleteBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//批量删除
	public String deleteBatchBudgChargeApply(List<Map<String, Object>> entityList)throws DataAccessException;
	
	//审核
	public String auditBudgChargeApply(List<Map<String, Object>> entityList)throws DataAccessException;
	
	//消审
	public String reAuditBudgChargeApply(List<Map<String, Object>> entityList)throws DataAccessException;
	
	//修改状态(提交或撤回)
	public String updateBudgChargeApplyState(List<Map<String, Object>> entityList)throws DataAccessException;
	
	//确认
	public String confirmBudgChargeApply(List<Map<String, Object>> entityList,Map<String, Object> entityMap)throws DataAccessException;
	
	//取消确认
	public String unConfirmBudgChargeApply(List<Map<String, Object>> entityList,Map<String, Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryBudgChargeApply(Map<String,Object> entityMap) throws DataAccessException;
	
	//按单号查询
	public BudgChargeApply queryBudgChargeApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	//打印-查询
	public List<Map<String,Object>> queryBudgChargeApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询收款单位
	public String queryBudgUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除-收款单位
	public String deleteBudgUnit(List<Map<String, Object>> entityList)throws DataAccessException;
	
	//添加页面跳转  查询当前用户数据
	public BudgChargeApply queryUserDataById(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryBudgUnitSelect(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMoneyApplyDet(Map<String, Object> mapVo);

	public String confirmBudgChargeCancel(List<Map<String, Object>> listVo, Map<String, Object> paramMapVo);

	/**
	 * 导入费用申请 
	 */
	public String importBudgChargeApply(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 导入费用申请(按行导入)
	 */
	public String importBudgChargeApplySingle(Map<String, Object> paraMap) throws DataAccessException;

}
