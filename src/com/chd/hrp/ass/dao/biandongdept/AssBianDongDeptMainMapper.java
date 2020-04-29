package com.chd.hrp.ass.dao.biandongdept;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;

public interface AssBianDongDeptMainMapper extends SqlMapper {
	List<AssZengJianMain> queryAssBianDongDept(Map<String, Object> entityMap)throws DataAccessException;

	List<AssZengJianMain> queryAssBianDongDept(Map<String, Object> entityMap,
			RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssBianDongDeptPrint(
			Map<String, Object> entityMap);

}
