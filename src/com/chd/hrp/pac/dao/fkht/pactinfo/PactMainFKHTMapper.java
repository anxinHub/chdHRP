package com.chd.hrp.pac.dao.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;

public interface PactMainFKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactMainFKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	List<PactMainFKHTEntity> queryByStateCode(Map<String, Object> page);

	List<PactMainFKHTEntity> queryByStateCode(Map<String, Object> entityMap, RowBounds rowBounds);

	Map<String, Object> queryForDepRec(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactFKHTSelectForLetter(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainFKHTForWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForWarning(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryPactMainFKHTForRetWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForRetWarning(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryPactMainFKHTForPayWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForPayWarning(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryPactMainFKHTForNearRepairWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForNearRepairWarning(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 资产购置申请信息查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssApplyFKHT(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产购置申请信息查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssApplyFKHT(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 定标信息查询 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactBidFKHT(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 定标信息查询 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactBidFKHT(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 *  复制主表数据到 copy表
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int copyMainData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 复制明细表数据到 copy表
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int copyDetData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 复制付款计划表数据到 copy表
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int copyPlanData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 变更页面 链接合同查看 数据查询（查询备份表数据)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public PactMainFKHTEntity queryByCodeCopy(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 合同 是否存在 签订后变动
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExeChange(Map<String, Object> map) throws DataAccessException;
	
	/**
	   * 添加时根据合同类型和名称查询该条数据是否存在，同一合同类型名称不可重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactFKHTMainByTypeAndName(Map<String, Object> entityMap) throws DataAccessException;

}
