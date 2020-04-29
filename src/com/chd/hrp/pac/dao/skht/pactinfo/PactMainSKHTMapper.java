package com.chd.hrp.pac.dao.skht.pactinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;

public interface PactMainSKHTMapper extends SqlMapper {

	String queryMaxId(Map<String, Object> mapVo);

	void deleteAllBatch(List<PactMainSKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	List<PactMainSKHTEntity> queryByStateCode(Map<String, Object> page);

	List<PactMainSKHTEntity> queryByStateCode(Map<String, Object> entityMap, RowBounds rowBounds);

	Map<String, Object> queryForDepRec(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactSKHTSelectForLetter(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactMainSKHTForWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainSKHTForWarning(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryPactMainSKHTForRetWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainSKHTForRetWarning(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryPactMainSKHTForPayWarning(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainSKHTForPayWarning(Map<String, Object> entityMap, RowBounds rowBounds);
    
	String queryExitsPactOthers(Map<String, Object> map);
	
	/**
	   * 添加时根据合同类型和名称查询该条数据是否存在，同一合同类型名称不可重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactSKHTMainByTypeAndName(Map<String, Object> entityMap) throws DataAccessException;
}
