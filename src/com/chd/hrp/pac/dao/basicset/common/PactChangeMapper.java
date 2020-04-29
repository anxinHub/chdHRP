package com.chd.hrp.pac.dao.basicset.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;

public interface PactChangeMapper extends SqlMapper {

	String queryMaxId(Map<String, Object> mapVo);

	String queryMainMoney(Map<String, Object> mapVo);

	void addChangeMoney(Map<String, Object> mapVo);

	List<Map<String, Object>> queryMainDet(Map<String, Object> mapVo);

	List<Map<String, Object>> queryChangeBefore(Map<String, Object> map);

	void copyPactMainFKHT(Map<String, Object> mapVo);

	void copyPactMainSKHT(Map<String, Object> mapVo);

	void copyPactMainFKXY(Map<String, Object> mapVo);

	void copyPactMainSKXY(Map<String, Object> mapVo);

	void copyPactDetFKHT(Map<String, Object> mapVo);

	void copyPactDetSKHT(Map<String, Object> mapVo);

	void copyPactDetFKXY(Map<String, Object> mapVo);

	void copyPactDetSKXY(Map<String, Object> mapVo);

	void copyPactPlanFKHT(Map<String, Object> mapVo);

	void copyPactPlanSKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeFKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeSKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeSKXY(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeFKXY(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeMoneyFKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainChangeMoneySKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMoneyChangeDet(Map<String, Object> mapVo);

	PactMainFKHTEntity queryPrePactMainFKHTByChangeCode(Map<String, Object> mapVo);

	PactMainSKHTEntity queryPrePactMainSKHTByChangeCode(Map<String, Object> mapVo);

	PactMainFKXYEntity queryPrePactMainFKXYByChangeCode(Map<String, Object> mapVo);

	PactMainSKXYEntity queryPrePactMainSKXYByChangeCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactPlanFKHTForPre(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactPlanSKHTForPre(Map<String, Object> mapVo);

	void deleteChangeMain(Map<String, Object> map);

	void deleteCopyMain(Map<String, Object> map);

	void deleteCopyDet(Map<String, Object> map);

	void deleteCopyMoney(Map<String, Object> map);

	void deleteCopyPlan(Map<String, Object> map);
	
	/**
	 * 签订后合同变动 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactMainChangeFKHTAfter(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 签订后合同变动 查询  分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactMainChangeFKHTAfter(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 签订后合同变动 修改页面信息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryPactChangeFKHTAfterByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 签订后付款合同变动  添加页面明细查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactMainChangeFKHTAfterDet(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 添加付款协议/收款协议变更表
	 * @param mapVo
	 */
	void addPactChangeXY(Map<String, Object> mapVo) throws DataAccessException;

}
