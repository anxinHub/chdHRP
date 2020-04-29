package com.chd.hrp.mat.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.warning.MatInvCertWarningMapper;
import com.chd.hrp.mat.service.warning.MatInvCertWarningService;
import com.github.pagehelper.PageInfo;

@Service("matInvCertWarningService")
public class MatInvCertWarningServiceImpl implements MatInvCertWarningService {

	private static Logger logger = Logger.getLogger(MatInvCertWarningServiceImpl.class);

	@Resource(name = "matInvCertWarningMapper")
	private final MatInvCertWarningMapper matInvCertWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMatInvCertWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = matInvCertWarningMapper.queryMatInvCertWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =
		 matInvCertWarningMapper.queryMatInvCertWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}
