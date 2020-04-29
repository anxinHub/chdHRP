package com.chd.hrp.htcg.dao.info;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgDrugTypeDict;



public interface HtcgDrugTypeDictMapper extends SqlMapper{
	
	
	public int addHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgDrugTypeDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcgDrugTypeDict> queryHtcgDrugTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugTypeDict> queryHtcgDrugTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcgDrugTypeDict queryHtcgDrugTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcgDrugTypeDict(List<Map<String, Object>> entityList)throws DataAccessException;
    

	public int updateHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
