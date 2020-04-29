package com.chd.hrp.pac.dao.basicset.mouldconfig;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

public interface PactMouldConfigItemMapper extends SqlMapper{
	
	List<Map<String, Object>> queryPactMouldConfig(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryDataPropertySelect(Map<String, Object> mapVo);
	
	int queryIsExistMouldByCode(Map<String, Object> map);
	
	List<Map<String, Object>> queryMouldItem(Map<String, Object> mapVo);
	
}
