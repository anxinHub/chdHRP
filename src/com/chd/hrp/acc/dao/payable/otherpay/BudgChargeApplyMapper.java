package com.chd.hrp.acc.dao.payable.otherpay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;

public interface BudgChargeApplyMapper extends SqlMapper{
	
	
	//添加
	public int addBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//修改
	public int updateBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//修改报销单金额
	public int updateAmount(Map<String,Object> entityMap)throws DataAccessException;
	
	//审核
	public int auditBudgChargeApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//消审
	public int reAuditBudgChargeApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//提交
	public int submitBudgChargeApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//删除
	public int deleteBudgChargeApply(Map<String,Object> entityMap)throws DataAccessException;
	
	//批量删除
	public int deleteBatchBudgChargeApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//查询
	public List<BudgChargeApply> queryBudgChargeApply(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询 带分页
	public List<BudgChargeApply> queryBudgChargeApply(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//按单号查询
	public BudgChargeApply queryBudgChargeApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	//确认
	public int confirmBudgChargeApply(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	//打印模板查询
	public Map<String,Object> queryChargeApplyByPrintTemlate(Map<String,Object> entityMap)throws DataAccessException;
	
	//打印查询
	public List<Map<String,Object>> queryBudgChargeApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
    //添加页面跳转  查询当前用户数据
	public BudgChargeApply queryUserDataById(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMoneyApplyDet(Map<String, Object> mapVo);

	public void confirmBudgChargeCancel(List<Map<String, Object>> entityList);
	
}	
