package com.chd.hrp.hpm.serviceImpl.report;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiTotalBonusControlMapper;
import com.chd.hrp.hpm.service.report.AphiTotalBonusControlService;

@Service("aphiTotalBonusControlService")
public class AphiTotalBonusControlServiceImpl implements AphiTotalBonusControlService {
	
	private static Logger logger = Logger.getLogger(AphiTotalBonusControlServiceImpl.class);
	
	@Resource(name = "aphiTotalBonusControlMapper")
	private final AphiTotalBonusControlMapper aphiTotalBonusControlMapper = null;

	@Override
	public String queryTotalBonusControlMainPage(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiTotalBonusControlMapper.queryTotalBonusControlMapperByCode(entityMap, rowBounds),sysPage.getTotal());
	}

}
