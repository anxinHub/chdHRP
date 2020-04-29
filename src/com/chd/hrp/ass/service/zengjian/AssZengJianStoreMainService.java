package com.chd.hrp.ass.service.zengjian;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

//import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;
public interface AssZengJianStoreMainService {

	public String queryAssZengJianStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssZengJianStoreMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
 