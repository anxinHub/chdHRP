package com.chd.hrp.eqc.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssEqcSelectMapper extends SqlMapper {
	/**
	/**
	 * 部门类型下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptType(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
 
	/**
	 * 部门分类下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptKind(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 部门性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 部门支出性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptOut(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出项目
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPaymentItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出项目下拉框 带变更号
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPaymentItemDict(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资产性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssNatures(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException ;
	
	/**
	 * 资产字典下拉框 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资产字典下拉框（无形资产）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssDictInassets(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException ;
	/**
	 * 职工下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 仓库  下拉框 不带NO
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHosStoreDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 仓库  下拉框 带NO
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHosStoreDictNo(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 仪器来源 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBusiDataSource(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资产卡片 设备下拉框   id（ass_card_no） text(ass_card_no ass_name )
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssCardDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 用户下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySysUser(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 服务项（收费类别 ） 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCostChargeKind(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 计量单位 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHosUnit(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 服务细项（收费项目 ） 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCostChargeItem(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 消耗资源 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssEqcConsumableItem(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 资源类型 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssEqcResourceType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 会计年度 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssYear(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException ;
	
	/**
	 * 机组下拉框 查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssEqUnit(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 分析项 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssAnalysisObject(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
}
