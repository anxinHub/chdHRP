package com.chd.hrp.ass.dao.biandongcategory;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;

public interface AssBianDongCategoryMainMapper extends SqlMapper {

	List<AssZengJianMain> queryAssBianDongCategory(Map<String, Object> entityMap)throws DataAccessException;

	List<AssZengJianMain> queryAssBianDongCategory(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssBianDongCategoryMainPrint(
			Map<String, Object> entityMap);

}
