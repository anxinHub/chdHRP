package com.chd.hrp.pac.dao.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;

public interface PactMainFKXYMapper extends SqlMapper{

	void deleteAllBatch(List<PactMainFKXYEntity> listVo);

	void updateState(Map<String, Object> map);

	List<PactMainFKXYEntity> queryPactFKXYForDeadline(Map<String, Object> mapVo);

	List<PactMainFKXYEntity> queryPactFKXYForDeadline(Map<String, Object> mapVo, RowBounds rowBounds);

	List<PactMainFKXYEntity> queryPactMainFKXYByStateCode(Map<String, Object> mapVo);

	List<PactMainFKXYEntity> queryPactMainFKXYByStateCode(Map<String, Object> mapVo, RowBounds rowBounds);
	
	/*
	 * 协议执行总额预警查询
	 */
	List<PactMainFKXYEntity> queryPactMoneyProgress(Map<String, Object> mapVo);

	List<PactMainFKXYEntity> queryPactMoneyProgress(Map<String, Object> mapVo, RowBounds rowBounds);
	
	/**
	   * 添加和修改时同一协议类型，合同名称不可重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactFKXYMainByTypeAndName(Map<String, Object> entityMap) throws DataAccessException;
	
}
