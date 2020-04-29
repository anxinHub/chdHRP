package com.chd.hrp.pac.service.skht.changeaftersign;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PactChangeAfterSignSKHTService {
/**
 * 查询合同名称和编码
 * @param mapVo
 * @return
 * @throws DataAccessException
 */
	public String querySKHTbyCus(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查看是否有未完成变更单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryExitsPactOthers(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 添加变更单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addChangeAfterSign(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据合同变更编码查询备份合同信息
	 */
	
	public Map<String, Object> querySKHTByChangeCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryPactDetSKHT(Map<String, Object> page);
	
	public String deletePactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	/**
	 * 提交
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSubmitPactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 * 撤销
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelPactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 *确认
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmPactMainSKHTC(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 * 更新备份合同
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updatePactMainSKHTC(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 更新变更单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateChangeAfterSign(Map<String, Object> mapVo) throws DataAccessException;
}
