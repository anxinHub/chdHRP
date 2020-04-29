package com.chd.hrp.pac.dao.basicset.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactSelectMapper extends SqlMapper {

	List<Map<String, Object>> queryHosEmpSelect(Map<String, Object> map);

	List<Map<String, Object>> queryHosSupSelect(Map<String, Object> map);

	List<Map<String, Object>> queryHosSourceDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCurSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHosProjDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDeptSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainFKHTSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMatInvDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMedInvDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactAssNoDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactElseSubDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactTypeSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactDocTypeSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPayTypeDict(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactBankSelectDict(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssTypeSelectDict(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPayTypeDictBySource(Map<String, Object> mapVo);

	List<Map<String, Object>> querySelcetFKHTNature(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactStateSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactPayCondSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTypeSKHTNatureSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactFKXYSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHosCusDictSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainSKHTSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactSKXYSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssTypeSelect(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryBtenDictSelect(Map<String, Object> map);
	/**
	 * 生产厂商下拉框
	 */
	public List<Map<String, Object>> queryHosFacDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 定标信息下拉框查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssTendInfo(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门下拉 带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptSelectDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 供应商下拉框带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosSupSelectDict(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 项目下拉框  带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosProjSelectDict(Map<String, Object> mapVo)throws DataAccessException ;
	
	/**
	 * 付款合同下拉框  有付款合同类型 权限  状态为 签订后  履行 12  状态   签订后合同变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactFKHTSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 付款协议下拉框  有付款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactFKXYSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 收款协议下拉框  有收款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactSKXYSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 合同模板配置方案 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 物资仓库 下拉框
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryMatStoreAll(Map<String, Object> map);
	
	/**
	 * 物资类别 下拉框
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryMatType(Map<String, Object> map);
	
	/**
	 * 计量单位 下拉框
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryHosUnit(Map<String, Object> map);
	
	/**
	 * 科室 下拉框（dept_name 非id text）
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDeptNameAndId(Map<String, Object> map);
	
}
