package com.chd.hrp.pac.service.jbrps;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
public interface PactMainJBRtService {
	
	public String queryPactAlteration(Map<String, Object> entityMap) throws DataAccessException;
	
	String add(Map<String ,Object>MapVo);
	
	String update(Map<String ,Object>MapVo);
}
