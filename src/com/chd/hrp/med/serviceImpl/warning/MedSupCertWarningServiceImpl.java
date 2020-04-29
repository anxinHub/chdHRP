package com.chd.hrp.med.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.warning.MedSupCertWarningMapper;
import com.chd.hrp.med.service.warning.MedInvCertWarningService;
import com.chd.hrp.med.service.warning.MedSupCertWarningService;
import com.github.pagehelper.PageInfo;

@Service("medSupCertWarningService")
public class MedSupCertWarningServiceImpl implements MedSupCertWarningService {

	private static Logger logger = Logger.getLogger(MedSupCertWarningServiceImpl.class);

	@Resource(name = "medSupCertWarningMapper")
	private final MedSupCertWarningMapper medSupCertWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMedSupCertWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = medSupCertWarningMapper.queryMedSupCertWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =medSupCertWarningMapper.queryMedSupCertWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}
