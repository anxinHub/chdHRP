package com.chd.hrp.pac.dao.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactDetFKHTEntity;

public interface PactDetFKHTMapper extends SqlMapper{


	void deleteAllBatch(List<PactDetFKHTEntity> listVo);

	void deleteByPactCode(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	Integer queryMaxDetailId(Map<String, Object> map);

	List<Map<String, Object>> queryByPactCodeList(Map<String, Object> map);
	
	/**
	 * 根据合同编码 查询 是否 存在 明细数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryExistsByPactCode(Map<String, Object> mapVo) throws DataAccessException;

}
