package com.chd.hrp.ass.dao.dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssMeasureKingDict;

public interface AssMeasureKingDictMapper extends SqlMapper{
	
	public int addAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	public List<AssMeasureKingDict> queryAssMeasureKingDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<AssMeasureKingDict> queryAssMeasureKingDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public AssMeasureKingDict queryAssMeasureKingDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryExistsCode(Map<String, Object> map_code);

	public List<Map<String,Object>> queryExistsName(Map<String, Object> map_name);

	public AssMeasureKingDict queryByName(Map<String, Object> entityMap)throws DataAccessException;
}
