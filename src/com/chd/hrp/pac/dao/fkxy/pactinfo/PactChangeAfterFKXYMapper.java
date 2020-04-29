package com.chd.hrp.pac.dao.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;

public interface PactChangeAfterFKXYMapper extends SqlMapper{

	void updateChangeFKXYState(Map<String, Object> map);
	
	void deleteChangeMain(Map<String, Object> map);

	void deleteCopyMain(Map<String, Object> map);

	void deleteCopyDet(Map<String, Object> map);
	
	int updateMainFKXY(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryMainFKXYC(Map<String,Object> map) throws DataAccessException;
	
	List<PactDetFKXYEntity> queryDetFKXYC(Map<String,Object> map) throws DataAccessException;
	
	void deleteAllBatch(List<PactDetFKXYEntity> list);
	
	void deletePactDetFKXYAllBatch(List<PactDetFKXYEntity> list);
	
	void deleteFKXYMainByCode(Map<String, Object> map);
	
	void updateMainFKXYC(Map<String,Object> entityMap)throws DataAccessException;
	
	void deleteDetFKXYC(List<PactDetFKXYEntity> list);
	
	void addDetFKXYC(List<PactDetFKXYEntity> list)throws DataAccessException;
	
	void updateState(Map<String, Object> map);

}
