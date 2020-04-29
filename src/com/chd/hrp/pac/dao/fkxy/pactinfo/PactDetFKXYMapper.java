package com.chd.hrp.pac.dao.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;

public interface PactDetFKXYMapper extends SqlMapper{

	void deletePactDetFKXYByPactCode(List<Map<String, Object>> listMap);

	void deleteAllBatch(List<PactDetFKXYEntity> list);

	Integer queryMaxDetailId(Map<String, Object> map);

	List<PactDetFKXYEntity> queryByPactCodeList(Map<String, Object> map);
	
	List<Map<String, Object>> queryPactFKXYMaterial(Map<String, Object> mapVo) throws DataAccessException;

}
