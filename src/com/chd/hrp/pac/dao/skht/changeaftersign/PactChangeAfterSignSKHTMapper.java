package com.chd.hrp.pac.dao.skht.changeaftersign;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactChangeAfterSignSKHTMapper extends SqlMapper {

	
	public List<Map<String, Object>> querySKHTbyCus(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryExitsPactOthers(Map<String, Object> mapVo) throws DataAccessException;
	
	public int addChangeSKHTMapper(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String, Object> querySKHTByChangeCode(Map<String, Object> mapVo)
			throws DataAccessException;
	/**
	 * 变动后明细备份
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int copyPactDetSKHT(Map<String, Object> mapVo) throws DataAccessException;
	
	public int addPactPlanSKHT(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存金额变动表数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactMoneycSKHT(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 以下方法为删除所有备份信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePactChangeSKHT(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int deletePactPlanSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int deletePactDetSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int deletePactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int deletePactMnoeySKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 * 提交
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateSubmitPactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 * 撤销
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateCancelPactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 *确认
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int confirmPactMainSKHTC(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 更新备份主表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePactMainSKHTC(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 更新合同备份表主表金额
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePactMainMoney(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 更新变更表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePactChangeSKHT(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 将收款合同备份表明细更新到主合同明细中
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactDetBySKHTC(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 将收款合同备份表收款计划更新到主合同中
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addPactPlanBySKHTC(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  List<Map<String, Object>> queryEdit(Map<String, Object> mapVo)throws DataAccessException;
	
}
