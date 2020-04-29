package com.chd.hrp.pac.dao.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;

public interface PactPlanFKHTMapper extends SqlMapper{


	void deleteAllBatch(List<PactPlanFKHTEntity> listVo);

	void deleteByPactCode(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	List<PactPlanFKHTEntity> queryForPay(Map<String, Object> mapVo);

	Integer queryMaxDetailId(Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryPactPlanList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改页面查询方法-不判断付款标识
	 * @param mapVo
	 * @return
	 */
	List<PactPlanFKHTEntity> queryForEdit(Map<String, Object> mapVo);

}
