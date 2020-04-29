package com.chd.hrp.pac.service.basicset.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;

public interface PactChangeService {

	String addPactMainChange(Map<String, Object> map, String pact_type_code);

	String queryPactMainChangeFKHT(Map<String, Object> mapVo);

	String queryPactMainChangeSKHT(Map<String, Object> mapVo);

	String queryPactMainChangeFKXY(Map<String, Object> mapVo);

	String queryPactMainChangeSKXY(Map<String, Object> mapVo);

	PactMainFKHTEntity queryPrePactMainFKHTByChangeCode(Map<String, Object> mapVo);

	PactMainSKHTEntity queryPrePactMainSKHTByChangeCode(Map<String, Object> mapVo);

	PactMainFKXYEntity queryPrePactMainFKXYByChangeCode(Map<String, Object> mapVo);

	PactMainSKXYEntity queryPrePactMainSKXYByChangeCode(Map<String, Object> mapVo);

	String queryPactMainChangeMoneyFKHT(Map<String, Object> mapVo);

	String queryPactMainChangeMoneySKHT(Map<String, Object> mapVo);

	String queryPactMoneyChangeDet(Map<String, Object> mapVo);

	String queryPactPlanFKHTForPre(Map<String, Object> mapVo);

	String queryPactPlanSKHTForPre(Map<String, Object> mapVo);

	void deleteChangeAndCopy(List<Map<String, Object>> listMap, String pact_type_code);

	List<Map<String, Object>> queryPactMainChangeMoneyFKHTPrint(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeMoneySKHTPrint(Map<String, Object> mapVo);
	
	/**
	 * 签订后合同变动 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactMainChangeFKHTAfter(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 签订后变更  添加时 根据合同编码 查询 是否存在 未确认 变更数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int checkUnconfirmData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 签订后合同变动 修改页面信息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryPactChangeFKHTAfterByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 签订后合同变动 添加页面 明细数据查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactMainChangeFKHTAfterDet(Map<String, Object> page) throws DataAccessException;
	/**
	 * 签订后合同变动 修改页面 明细数据查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactChangeFKHTAfterDetUpdate(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 签订后合同变动  修改
	 * @param mapVo
	 * @param string
	 * @return
	 * @throws DataAccessException
	 */
	public String updatePactMainChange(Map<String, Object> mapVo, String string) throws DataAccessException;
	/**
	 *  签订后合同变动  提交 撤回
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateChangeFKHTState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmPactChangeFKHTAfter(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 签订后变更 删除
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deletePactMainFKHTAfter(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 签订后变动  备份表数据 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updatePactMainFKHTCopy(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 收付款协议添加备份表
	 * @param map
	 * @param pact_type_code
	 * @return
	 */
	void addPactMainXYChange(Map<String, Object> map, String pact_type_code);
}
