package com.chd.hrp.ass.dao.zengjian;

import com.chd.base.SqlMapper;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public interface AssZengJianStoreMainMapper extends SqlMapper {

	List<Map<String, Object>> queryAssZengJianStore(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssZengJianStore(Map<String, Object> entityMap,
			RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssZengJianStoreMainPrint(
			Map<String, Object> entityMap);

}
 