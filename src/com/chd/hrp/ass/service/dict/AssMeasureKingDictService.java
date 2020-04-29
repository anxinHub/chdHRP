
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.ass.entity.dict.AssMeasureKingDict;
 

public interface AssMeasureKingDictService {

	public String addAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String deleteAssMeasureKingDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAssMeasureKingDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	public String queryAssMeasureKingDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public AssMeasureKingDict queryAssMeasureKingDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String readAssMeasureKingDictFiles(Map<String, Object> mapVo);

	String assMeasureKingDictImport(Map<String, Object> mapVo);

	public AssMeasureKingDict queryByName(Map<String, Object> entityMap)throws DataAccessException;
	
}
