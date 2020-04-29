package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.TmpAccLedger;
import com.chd.hrp.acc.entity.TmpAccSubj;
/**
 * 新旧制度衔接
 * @author yang
 *
 */
public interface AccNewOldJoinMapper extends SqlMapper {

	/**
	 * 查旧科目
	 */
	public List<TmpAccSubj> queryAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	/**
	 * 查旧科目(分页)
	 */
	public List<TmpAccSubj> queryAccSubjOld(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查新科目
	 */
	public List<TmpAccSubj> queryAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;
	/**
	 * 查新科目(分页)
	 */
	public List<TmpAccSubj> queryAccSubjNew(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 通过科目编码删除（批量）
	 */
	public void deleteAccSubjOldByCodeBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 通过科目编码删除新科目（批量）
	 */
	public void deleteAccSubjNewByCodeBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 添加旧科目（批量）
	 * @param saveList
	 * @throws DataAccessException
	 */
	public void addAccSubjOldBatch(List<Map<String, Object>> saveList) throws DataAccessException;
	
	/**
	 * 添加新科目（批量）
	 * @param saveList
	 * @throws DataAccessException
	 */
	public void addAccSubjNewBatch(List<Map<String, Object>> saveList) throws DataAccessException;
	
	/**
	 * 添加一个临时会计科目（旧）
	 */
	public void addAccSubjOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 添加一个临时会计科目（新）
	 */
	public void addAccSubjNew(Map<String, Object> paramMap) throws DataAccessException;
	
	//导入系统旧科目数据
	public int importSystemAccSubjOldOldSubj(Map<String, Object> paramMap);
	
	//删除导入时旧数据
	public void deleteimportSystemAccSubjNewSubj(Map<String, Object> paramMap);
	
	//查询需要导入的新数据并set拼音码
	public List<TmpAccSubj> queryimportSystemAccSubjNewSubj(
			Map<String, Object> paramMap);
	
	//导入系统新科目
	public int importSystemAccSubjNewSubj(List<TmpAccSubj> tmpList);
	
	/**
	 * 查新余额
	 */
	public List<Map<String, Object>> queryAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查新余额（分页）
	 */
	public List<Map<String, Object>> queryAccLedgerNew(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查旧余额
	 */
	public List<Map<String, Object>> queryAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查旧余额（分页）
	 */
	public List<Map<String, Object>> queryAccLedgerOld(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查新旧科目对应关系表
	 */
	public List<Map<String, Object>> queryAccSubjMap(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查新旧科目对应关系表（分页）
	 */
	public List<Map<String, Object>> queryAccSubjMap(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 删除旧余额（批量）
	 */
	public void deleteAccLedgerOldByCodeBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 删除新余额（批量）
	 */
	public void deleteAccLedgerNewByCodeBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 通过主键取一个临时会计科目（新、旧）
	 * 参数中包含集团、医院、账套、表名、科目编码(主键)
	 */
	public TmpAccSubj getTmpAccSubjByPK(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 通过主键 更新会计科目（旧）
	 */
	public void updateAccSubjOldByPK(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 通过主键 更新会计科目（新）
	 */
	public void updateAccSubjNewByPK(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 取所有子级科目
	 */
	public List<TmpAccSubj> getAllSubSubj(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 更新科目全称（批量）
	 */
	public void updateSubjNameAllBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	//删除已经存在的余额数据
	public int deleteimportSystemAccSubjOld(Map<String, Object> paramMap);
	
	/**
	 * 导入系统余额数据
	 * @param paramMap
	 * @return
	 */
	public int importSystemAccSubjOld(Map<String, Object> paramMap);
	
	//导入Excel前删除重复数据
	public void deleteAccSubjAccLedgerOld(@Param("list")List<Map<String, Object>> allList);
	
	//导入Excel旧余额数据
	public int addAccSubjAccLedgerOld(@Param("list")List<Map<String, Object>> allList);
	
	//旧科目转换新科目
	public void regenerateAccSubjOld(Map<String, Object> map);
	
	/**
	 * 取一个科目余额（要加上表名 table_name : tmp_acc_ledger_old/tmp_acc_ledger_new）
	 */
	public TmpAccLedger getTmpAccLedgerByPK(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 导入辅助核算---来自系统
	 */
	public void importLedgerCheckFromSys(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询辅助核算基本信息
	 */
	public List<Map<String, Object>> querySubjCheck(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public Map<String, Object> queryCheckItemTable(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询科目的辅助核算
	 */
	public List<Map<String, Object>> queryLedgerCheckList(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除余额
	 */
	public void deleteTmpAccLedger(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 保存余额
	 */
	public void saveTmpAccLedger(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 通过科目编号删除辅助核算
	 */
	public void deleteTmpAccLedgerCheckBySubjCode(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 保存辅助核算
	 */
	public void saveTmpAccLedgerCheckBatch(@Param("map") Map<String, Object> paramMap, 
			@Param("list") List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 初始添加旧余额
	 */
	public void addInitAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 初始添加新余额
	 */
	public void addInitAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException;
	
	//旧余额转换新余额
	public void regenerateAccSubjnewLedger(Map<String, Object> map);
	
	//重新生成科目映射关系
	public void regenerateAccSubjnewOldMap(Map<String, Object> map);
	
	//生成余额后再生成辅助核算
	public void regenerateAccSubjnewLedgerFuzhu(Map<String, Object> map);
	
	/**
	 * 查辅助核算项
	 */
	public Map<String, Object> queryLedgerCheckCodeAndName(Map<String, Object> sqlMap) throws DataAccessException;
	
	/**
	 * 删除辅助核算（批量）
	 */
	public void deleteTmpAccLedgerCheckBatch(@Param("map") Map<String, Object> paramMap, 
			@Param("list") List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 查余额sum
	 */
	public Map<String, Object> queryLedgerSumBySubjCode(Map<String, Object> t) throws DataAccessException;
	
	/**
	 * 更新余额
	 */
	public void updateTmpAccLedger(Map<String, Object> ledgerSum) throws DataAccessException;
	//查询是否存在非法的辅助核算名称
	public List<Map<String, Object>> queryaddOfficiallyNewSubj(Map<String, Object> paramMap);
	
	//查询新年度是否存在想同的科目
	public int queryaddOfficiallyAccSubj(
			Map<String, Object> paramMap);
	
	//新科目载入系统
	public int addOfficiallyNewSubj(List<Map<String, Object>> newlist);
	
	//查询旧科目下拉框
	public List<Map<String, Object>> querySubjCodeOldSelect();
	
	//查询新科目下拉框
	public List<Map<String, Object>> querySubjcodenewSelect(Map<String, Object> paramMap);
	
	//查询添加拼音码编码的数据
	public List<Map<String, Object>> queryNewList(Map<String, Object> paramMap);
	
	/**
	 * 新余额与辅助核算正式载入
	 */
	public void ledgerAndCheckIntoSys(Map<String, Object> paramMap) throws DataAccessException;
	
	//修改映射关系
	public int updateTranMap(Map<String, Object> paramMap);
	
	//删除映射关系数据
	public void deleteUpdateTranMap(Map<String, Object> paramMap);
	
	//批量删除映射关系
	public int deleteTranMapList(List<Map> list);
	
	/**
	 * 查系统有没有新余额
	 */
	public int queryNewLedgerExists(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查科目有没有下级(临时表新科目)
	 */
	public List<Map<String, Object>> queryExistsLastForNew(@Param("set") Set<String> subjCodeSet) throws DataAccessException;
	
	/**
	 * 查科目有没有下级(临时表旧科目)
	 */
	public List<Map<String, Object>> queryExistsLastForOld(@Param("set") Set<String> subjCodeSet) throws DataAccessException;
	
	/**
	 * 更新是否末级
	 */
	public void updateSubjIsLastBatch(List<Map<String, Object>> updateList) throws DataAccessException;
	
	/**
	 * 查询科目是否末级
	 */
	public int queryTmpSubjIsLast(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除新余额（批量）
	 */
	public void deleteTmpAccLedgerNewBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 添加新余额（批量）
	 */
	public int addTmpAccLedgerNewBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 批量删除科目映射关系（通过主键）
	 */
	public void deleteTmpAccTranMapBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 批量添加科目映射关系
	 */
	public int addTmpAccTranMapBatch(List<Map<String, Object>> list) throws DataAccessException;
}
