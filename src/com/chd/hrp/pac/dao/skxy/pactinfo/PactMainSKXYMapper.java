package com.chd.hrp.pac.dao.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;

public interface PactMainSKXYMapper extends SqlMapper{

	void deleteAllBatch(List<PactMainSKXYEntity> listVo);

	void updateState(Map<String, Object> map);

	List<PactMainSKXYEntity> queryPactSKXYForDeadline(Map<String, Object> mapVo);

	List<PactMainSKXYEntity> queryPactSKXYForDeadline(Map<String, Object> mapVo, RowBounds rowBounds);

	List<PactMainSKXYEntity> queryPactMainSKXYByStateCode(Map<String, Object> mapVo);

	List<PactMainSKXYEntity> queryPactMainSKXYByStateCode(Map<String, Object> mapVo, RowBounds rowBounds);
	
	/**
	   * 添加时根据协议类型和名称查询该条数据是否存在，同一协议类型名称不可重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactSKXYMainByTypeAndName(Map<String, Object> entityMap) throws DataAccessException;

}
