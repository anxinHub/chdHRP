package com.chd.hrp.pac.dao.fkht.change;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;

public interface PactPlanFKHTCMapper extends SqlMapper{
	/**
	 * 查询最带id 号
	 * @param planMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryMaxDetailId(Map<String, Object> planMap) throws DataAccessException;

	/**
	 * 删除 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchByEntity(List<PactPlanFKHTEntity> listVo) throws DataAccessException;


}
