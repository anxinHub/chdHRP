package com.chd.hrp.pac.dao.fkht.change;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactChangeFKHTMapper extends SqlMapper{
	
	/**
	 * 签订后变动  复制合同主表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int copyMainData(Map<String, Object> copyMap) throws DataAccessException;
	
	/**
	 * 删除复制合同主表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopyMainData(Map<String, Object> copyMap)throws DataAccessException;
	
	/**
	 * 签订后变动  复制合同明细表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int copyDetData(Map<String, Object> copyMap) throws DataAccessException;
	
	/**
	 * 删除复制合同明细数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopyDetData(Map<String, Object> copyMap)throws DataAccessException;
	
	/**
	 * 签订后变动  复制合同付款计划表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int copyPlanData(Map<String, Object> copyMap) throws DataAccessException;
	
	/**
	 * 删除复制合同付款计划表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopyPlanData(Map<String, Object> copyMap)throws DataAccessException;
	
	/**
	 * 签订后变动  复制合同明细资金来源数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int copySourceData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 删除复制合同付款计划表数据
	 * @param copyMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopySourceData(Map<String, Object> copyMap)throws DataAccessException;
	
	/**
	 * 签订前  复制合同明细资金来源数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int copySourceData1(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 签订后变动 提交/撤回 / 确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateChangeFKHTState(List<Map<String, Object>> listVo) throws DataAccessException ;
	/**
	 * 签订后变动 确认时  删除 明细资金来源数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePactSource(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 *  签订后变动 确认时  删除 明细数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePactDet(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 *  签订后变动 确认时  删除 付款计划数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePactPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 *  签订后变动 确认时  删除 合同主表源数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePactMain(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 签订后变动 确认时  添加 合同主表源数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactMain(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 *  签订后变动 确认时  添加 明细数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactDet(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 *  签订后变动 确认时  添加 明细资金来源数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactSource(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 签订后变动 确认时  添加 付款计划数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 签订后变更 添加时 根据合同编码 查询 是否存在 未确认 变更数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int checkUnconfirmData(Map<String, Object> mapVo) throws DataAccessException;



}
