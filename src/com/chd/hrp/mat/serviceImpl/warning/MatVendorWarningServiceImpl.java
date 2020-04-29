package com.chd.hrp.mat.serviceImpl.warning;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.warning.MatVendorWarningMapper;
import com.chd.hrp.mat.service.warning.MatVendorWarningService;
import com.github.pagehelper.PageInfo;

@Service("matVendorWarningService")
public class MatVendorWarningServiceImpl implements MatVendorWarningService {

	private static Logger logger = Logger.getLogger(MatVendorWarningServiceImpl.class);

	@Resource(name = "matVendorWarningMapper")
	private final MatVendorWarningMapper matVendorWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMatVendorWarning(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> warnTypes;
		
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		
		if(entityMap.get("warn_type_code") == null || entityMap.get("warn_type_code").toString().equals("")){
			warnTypes = matVendorWarningMapper.queryWarnType(entityMap);
			for(Map<String, Object> warnType : warnTypes){
				entityMap.put("warn_way", warnType.get("WARN_WAY").toString());
				if(warnType.get("WARN_WAY").toString().equals("2") || warnType.get("WARN_WAY").toString().equals("4"))
					entityMap.put("days", warnType.get("DAYS"));
				List<Map<String, Object>> partResult = matVendorWarningMapper.queryMatVendorWarnByDays(entityMap);
				for(Map<String, Object> oneResult : partResult)
					oneResult.put("warn_way", warnType.get("WARN_WAY").toString());
				result.addAll(partResult);
			}
			
			Set<String> duplicateSet = new HashSet<>();
			for(Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext(); ){
				Map<String, Object> tempMap = (Map<String, Object>)iterator.next();
				if(duplicateSet.contains(tempMap.get("cert_code").toString()))
					iterator.remove();
				else
					duplicateSet.add(tempMap.get("cert_code").toString());
			}
			
			return ChdJson.toJson(result);
		}
		
		warnTypes = matVendorWarningMapper.queryWarnType(entityMap);
		Map<String, Object> warnType = warnTypes.get(0);
		entityMap.put("warn_way", warnType.get("WARN_WAY").toString());
		if(warnType.get("WARN_WAY").toString().equals("2") || warnType.get("WARN_WAY").toString().equals("4"))
			entityMap.put("days", warnType.get("DAYS"));
		result = matVendorWarningMapper.queryMatVendorWarnByDays(entityMap);
		for(Map<String, Object> oneResult : result)
			oneResult.put("warn_way", warnType.get("WARN_WAY").toString());
		return ChdJson.toJson(result);
	}
}
