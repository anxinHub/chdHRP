package com.chd.hrp.ass.dao.asspaysyncpact;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;

public interface AssPaySyncPactMapper extends SqlMapper{
	/**
	 * 查询系统参数  （para_code=11003）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	AccPara queryAccParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询资产预付款主表、明细表及发票主表
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAssPrePayByNo(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 查询资产预付款主表、明细表及发票主表
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAssPayByNo(Map<String,Object> map)throws DataAccessException;
	
	/**
	   * 添加付款合同付款主表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	int addPactPayMainFKHT(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	   * 添加付款合同付款明细表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	int addPactPayDetFKHT(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询预付款明细表、预付款支付方式表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAssPrePayDetByNo(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 查询付款明细表、付款支付方式表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAssPayDetByNo(Map<String,Object> map)throws DataAccessException;
	
	/**
	   * 查询款合同付款明细表最大明细号
	 * @param map
	 * @return
	 */
	Integer queryMaxDetailId(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 根据单据号查询付款合同付款主表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryPactPayMainFKHTByPayNo(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 删除-付款合同付款主表
	 * @param listVo
	 */
	int deletePactPayMainFKHT(List<Map<String,Object>> listVo) throws DataAccessException;
	
	/**
	 * 删除-付款合同付款明细表
	 * @param listVo
	 */
	int deletePactPayDetFKHT(List<Map<String,Object>> listVo) throws DataAccessException;
	
	/**
	 * 删除-付款合同按计划付款
	 * @param listVo
	 */
	int deletePactPayPlanFKHT(List<Map<String,Object>> listVo) throws DataAccessException;
	
	/**
	 * 更新付款合同付款主表状态
	 * @param map
	 */
	int updatePactPayMainFKHTState(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据单据号查询付款合同付款主表、付款计划、按计划付款表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryPactPlanFKHT(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 更新付款合同付款计划-已付金额、付款标志
	 * @param map
	 */
	int updatePactPlanFKHT(List<Map<String,Object>> list) throws DataAccessException;
	
	/**
	 * 取资产付款明细表中发票流水号关联发票卡片期号明细，按照发票、期号汇总得到对应的发票金额
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Double queryAssDetStage(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 根据合同编号、期号查询付款计划得到计划明细号、期号、计划金额等
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryPactPlanStage(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 更新付款合同按计划付款
	 * @param map
	 */
	int updatePactPayPlanFKHT(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 查询付款合同按计划付款 PACT_PAY_PLAN_FKHT
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Double queryPactPayPlanFKHT(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 查询合同接口来源表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryPactInterfaceType(Map<String,Object> map)throws DataAccessException;
	
	/**
	 * 查询用户表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryUserDict(Map<String,Object> map)throws DataAccessException;
}
