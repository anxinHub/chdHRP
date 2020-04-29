package com.chd.hrp.pac.dao.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;

public interface PactChangeAfterSKXYMapper extends SqlMapper{

	void updateChangeSKXYState(Map<String, Object> map);
	
	void deleteChangeMain(Map<String, Object> map);

	void deleteCopyMain(Map<String, Object> map);

	void deleteCopyDet(Map<String, Object> map);
	
	int updateMainSKXY(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryMainSKXYC(Map<String,Object> map) throws DataAccessException;
	
	List<PactDetSKXYEntity> queryDetSKXYC(Map<String,Object> map) throws DataAccessException;
	
	void deleteAllBatch(List<PactDetSKXYEntity> list);
	
	void deletePactDetSKXYAllBatch(List<PactDetSKXYEntity> list);
	
	void deleteSKXYMainByCode(Map<String, Object> map);
	
	void updateMainSKXYC(Map<String,Object> entityMap)throws DataAccessException;
	
	void deleteDetSKXYC(List<PactDetSKXYEntity> list);
	
	void addDetSKXYC(List<PactDetSKXYEntity> list)throws DataAccessException;
	
	void updateState(Map<String, Object> map);

}
