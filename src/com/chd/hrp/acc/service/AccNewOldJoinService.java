package com.chd.hrp.acc.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.TmpAccLedger;
import com.chd.hrp.acc.entity.TmpAccSubj;

/**
 * 新旧制度衔接
 * @author yang
 *
 */
public interface AccNewOldJoinService {

	/**
	 * 查旧科目
	 */
	public String queryAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查上余额
	 */
	public String queryAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查新科目
	 */
	public String queryAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查新余额
	 */
	public String queryAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 导入旧科目
	 */
	public String importAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 导入新科目
	 */
	public String importAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 导入旧余额
	 */
	public String importAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 导入新余额
	 */
	public String importAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除新科目
	 */
	public String deleteAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除旧科目
	 */
	public String deleteAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	//导入系统旧科目
	public String importSystemAccSubjOldOldSubj(Map<String, Object> paramMap);

	//导入系统新科目
	public String importSystemAccSubjNewSubj(Map<String, Object> paramMap);

	/**
	 * 查新旧科目对应 关系表
	 */
	public String queryAccSubjMap(Map<String, Object> page) throws DataAccessException;

	/**
	 * 删除旧余额
	 */
	public String deleteAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除新余额
	 */
	public String deleteAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 添加科目（旧）
	 */
	public String addTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 添加科目（新）
	 */
	public String addTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取一个会计科目（旧）
	 */
	public TmpAccSubj getTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 取一个会计科目（新）
	 */
	public TmpAccSubj getTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 更新会计科目（旧）
	 */
	public String updateTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 更新会计科目（新）
	 */
	public String updateTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;

	//导入系统旧余额
	public String importSystemAccSubjOldLedger(
			Map<String, Object> paramMap);

	//旧科目转换新科目
	public String regenerateAccSubjOld();

	/**
	 * 取一个余额（旧）
	 */
	public TmpAccLedger getTmpAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 取一个余额（新）
	 */
	public TmpAccLedger getTmpAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 辅助核算表头
	 */
	public String getSubjCheckTitle(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查询旧科目辅助核算
	 */
	public String queryLedgerCheckList(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 保存余额
	 */
	public String saveTmpAccLedger(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 通过excel导入辅助核算
	 */
	public String impTmpAccLedgerCheckByExcel(Map<String, Object> paramMap) throws DataAccessException;

	//从旧科目生成新科目
	public String regenerateAccSubjnewLedger();

	//重新生成科目映射管理
	public String regenerateAccSubjnewOldMap();
	//正式载入新科目
	public String addOfficiallyNewSubj(Map<String, Object> paramMap);

	//查询旧科目下拉框
	public List<Map<String, Object>> querySubjCodeOldSelect();

	//查询新科目下拉框
	public List<Map<String, Object>> querySubjcodenewSelect(Map<String, Object> paramMap);

	/**
	 * 正式载入新余额与辅助核算
	 */
	public String ledgerAndCheckIntoSys(Map<String, Object> paramMap) throws DataAccessException;

	//映射关系修改
	public String updateTranMap(Map<String, Object> paramMap);

	//批量删除对应关系
	public String deleteAccNewOldMap(Map<String, Object> paramMap);

	/**
	 * 查新余额是否已经存在系统中
	 */
	public String queryNewLedgerExists(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 科目映射关系excel导入
	 */
	public String importAccTranMap(Map<String, Object> paramMap) throws DataAccessException;

}
