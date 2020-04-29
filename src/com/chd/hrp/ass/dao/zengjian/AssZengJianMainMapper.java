package com.chd.hrp.ass.dao.zengjian;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public interface AssZengJianMainMapper extends SqlMapper {

	List<Map<String, Object>> queryAssZengJian(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssZengJian(Map<String, Object> entityMap,
			RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssZengJianMainPrint(
			Map<String, Object> entityMap);

}
