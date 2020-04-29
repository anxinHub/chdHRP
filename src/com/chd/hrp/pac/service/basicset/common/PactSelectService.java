package com.chd.hrp.pac.service.basicset.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PactSelectService {

	String queryHosEmpSelect(Map<String, Object> mapVo);

	String queryHosSupSelect(Map<String, Object> mapVo);

	String queryHosSourceDictSelect(Map<String, Object> mapVo);

	String queryAccCurSelect(Map<String, Object> mapVo);

	String queryHosProjDictSelect(Map<String, Object> mapVo);

	String queryDeptSelect(Map<String, Object> mapVo);

	String queryPactMainFKHTSelect(Map<String, Object> mapVo);

	String queryPactMatInvDictSelect(Map<String, Object> mapVo);

	String queryPactMedInvDictSelect(Map<String, Object> mapVo);

	String queryPactAssNoDictSelect(Map<String, Object> mapVo);

	String queryPactElseSubDictSelect(Map<String, Object> mapVo);

	String queryPactTypeSelect(Map<String, Object> mapVo, String table_type);

	String queryPactDocTypeSelect(Map<String, Object> mapVo);

	String queryPayTypeDict(Map<String, Object> mapVo);

	String queryPactBankSelectDict(Map<String, Object> mapVo);

	String queryAssTypeSelectDict(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPayTypeDictBySource(Map<String, Object> mapVo);

	String querySelcetFKHTNature(Map<String, Object> mapVo);

	String queryPactStateSelect(Map<String, Object> mapVo);

	String queryPactPayCondSelect(Map<String, Object> mapVo);

	String queryDictSelect(Map<String, Object> mapVo);

	String queryTypeSKHTNatureSelect(Map<String, Object> mapVo);

	String queryPactFKXYSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHosCusDictSelect(Map<String, Object> mapVo);

	String queryPactMainSKHTSelect(Map<String, Object> mapVo);

	String queryPactSKXYSelect(Map<String, Object> mapVo);

	String queryAssTypeSelect(Map<String, Object> mapVo);
	String queryBtenDictSelect(Map<String, Object> mapVo);
	/**
	 * 生产厂商 下来框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosFacDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 定标信息 下拉框查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTendInfo(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 部门下拉 带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptSelectDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 供应商下拉框 带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosSupSelectDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目下拉框  带变更id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosProjSelectDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 付款合同下拉框  有付款合同类型 权限  状态为 签订后  履行 12  状态   签订后合同变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactFKHTSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 付款协议下拉框  有付款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactFKXYSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 收款协议下拉框  有收款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactSKXYSelectPerm(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 合同模板配置方案 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactTemplate(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 物资仓库下拉框
	 */
	public String queryMatStoreAll(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 物资类别下拉框
	 * 
	 */
	public String queryMatType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计量单位下拉框
	 * 
	 */
	public String queryHosUnit(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室 下拉框（dept_name）
	 * 
	 */
	public String queryDeptNameAndId(Map<String, Object> mapVo) throws DataAccessException;
}
