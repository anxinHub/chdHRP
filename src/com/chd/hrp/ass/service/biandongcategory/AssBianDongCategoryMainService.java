package com.chd.hrp.ass.service.biandongcategory;
import java.util.List;
import java.util.Map;



import org.springframework.dao.DataAccessException;
public interface AssBianDongCategoryMainService {

	String queryAssBianDongCategory(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssBianDongCategoryMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
